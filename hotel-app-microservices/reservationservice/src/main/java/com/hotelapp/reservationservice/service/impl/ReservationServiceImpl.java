package com.hotelapp.reservationservice.service.impl;

import com.hotelapp.reservationservice.client.RoomServiceClient;
import com.hotelapp.reservationservice.client.UserServiceClient;
import com.hotelapp.reservationservice.dto.*;
import com.hotelapp.reservationservice.entity.Reservation;
import com.hotelapp.reservationservice.enums.ReservationStatus;
import com.hotelapp.reservationservice.exception.ValidationException;
import com.hotelapp.reservationservice.mapper.ReservationMapper;
import com.hotelapp.reservationservice.repository.ReservationRepository;
import com.hotelapp.reservationservice.service.ReservationService;
import com.hotelapp.reservationservice.specification.ReservationSpecification;
import com.hotelapp.reservationservice.validator.ReservationValidator;
import com.hotelapp.sharedmodels.dto.RoomSummaryDTO;
import com.hotelapp.sharedmodels.dto.UserSummaryDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationValidator reservationValidator;
    private final UserServiceClient userServiceClient;
    private final RoomServiceClient roomServiceClient;

    private static final String RESERVATION_NOT_FOUND = "Reservation not found with id: ";

    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO dto) {
        reservationValidator.validate(dto);

        UserSummaryDTO user = userServiceClient.getUserById(dto.getUserId());
        RoomSummaryDTO room = roomServiceClient.getRoomById(dto.getRoomId());

        Reservation reservation = reservationMapper.toEntity(dto);
        reservation.setStatus(ReservationStatus.PENDING);

        // Store snapshot data so reads don't require cross-service calls
        reservation.setUserFirstName(user.getFirstName());
        reservation.setUserLastName(user.getLastName());
        reservation.setRoomNumber(room.getRoomNumber());
        reservation.setRoomTypeName(room.getRoomTypeName());
        reservation.setRoomDescription(room.getRoomTypeDescription());
        reservation.setPricePerNight(room.getPricePerNight());

        long nights = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        reservation.setTotalPrice(room.getPricePerNight().multiply(BigDecimal.valueOf(nights)));

        Reservation saved = reservationRepository.save(reservation);

        // Notify room-service so it can update its local availability table
        roomServiceClient.syncBooking(new RoomBookingSyncDTO(
                saved.getId(), saved.getRoomId(),
                saved.getCheckInDate(), saved.getCheckOutDate(),
                saved.getStatus().name()
        ));

        return reservationMapper.toDto(saved);
    }

    @Override
    public ReservationResponseDTO getReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(RESERVATION_NOT_FOUND + reservationId));
        return reservationMapper.toDto(reservation);
    }

    @Override
    public List<ReservationResponseDTO> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @Override
    public List<ReservationResponseDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @Override
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(RESERVATION_NOT_FOUND + reservationId));
        reservationRepository.delete(reservation);
    }

    @Override
    public ReservationResponseDTO cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(RESERVATION_NOT_FOUND + reservationId));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new ValidationException("Reservation is already cancelled");
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation saved = reservationRepository.save(reservation);
        syncStatusChange(saved);
        return reservationMapper.toDto(saved);
    }

    @Override
    public ReservationResponseDTO approveReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(RESERVATION_NOT_FOUND + reservationId));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new ValidationException("Only PENDING reservations can be approved");
        }
        reservation.setStatus(ReservationStatus.CONFIRMED);
        Reservation saved = reservationRepository.save(reservation);
        syncStatusChange(saved);
        return reservationMapper.toDto(saved);
    }

    @Override
    public ReservationResponseDTO rejectReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(RESERVATION_NOT_FOUND + reservationId));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new ValidationException("Only PENDING reservations can be rejected");
        }
        reservation.setStatus(ReservationStatus.REJECTED);
        Reservation saved = reservationRepository.save(reservation);
        syncStatusChange(saved);
        return reservationMapper.toDto(saved);
    }

    @Override
    public List<ReservationResponseDTO> search(ReservationSearchRequestDTO request) {
        Specification<Reservation> specification = ReservationSpecification.filter(request);
        return reservationRepository.findAll(specification).stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    private void syncStatusChange(Reservation reservation) {
        roomServiceClient.syncBooking(new RoomBookingSyncDTO(
                reservation.getId(), reservation.getRoomId(),
                reservation.getCheckInDate(), reservation.getCheckOutDate(),
                reservation.getStatus().name()
        ));
    }
}