package com.hotelapp.reservationservice.mapper;

import com.hotelapp.reservationservice.dto.ReservationRequestDTO;
import com.hotelapp.reservationservice.dto.ReservationResponseDTO;
import com.hotelapp.reservationservice.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public Reservation toEntity(ReservationRequestDTO dto) {
        if (dto == null) return null;
        Reservation reservation = new Reservation();
        reservation.setUserId(dto.getUserId());
        reservation.setRoomId(dto.getRoomId());
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());
        return reservation;
    }

    public ReservationResponseDTO toDto(Reservation reservation) {
        if (reservation == null) return null;
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setReservationId(reservation.getId());
        dto.setCheckInDate(reservation.getCheckInDate());
        dto.setCheckOutDate(reservation.getCheckOutDate());
        dto.setStatus(reservation.getStatus().name());
        dto.setTotalPrice(reservation.getTotalPrice());
        dto.setRoomId(reservation.getRoomId());
        dto.setRoomNumber(reservation.getRoomNumber());
        dto.setRoomName(reservation.getRoomTypeName());
        dto.setRoomDescription(reservation.getRoomDescription());
        dto.setUserId(reservation.getUserId());
        dto.setUserFirstName(reservation.getUserFirstName());
        dto.setUserLastName(reservation.getUserLastName());
        return dto;
    }
}