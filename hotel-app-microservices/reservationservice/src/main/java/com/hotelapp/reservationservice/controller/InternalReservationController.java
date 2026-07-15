package com.hotelapp.reservationservice.controller;

import com.hotelapp.reservationservice.entity.Reservation;
import com.hotelapp.reservationservice.repository.ReservationRepository;
import com.hotelapp.sharedmodels.dto.ReservationSummaryDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// Internal endpoints used only by other microservices, not exposed to frontend clients.
@RestController
@RequestMapping("/internal/reservations")
@RequiredArgsConstructor
public class InternalReservationController {

    private final ReservationRepository reservationRepository;

    @GetMapping("/{id}")
    public ReservationSummaryDTO getReservationById(@PathVariable Long id) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found: " + id));

        return new ReservationSummaryDTO(
                r.getId(),
                r.getUserId(),
                r.getRoomId(),
                r.getUserFirstName(),
                r.getUserLastName(),
                r.getRoomNumber(),
                r.getRoomTypeName(),
                r.getRoomDescription(),
                r.getStatus().name(),
                r.getCheckOutDate()
        );
    }
}