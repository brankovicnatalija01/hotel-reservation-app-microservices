package com.hotelapp.reservationservice.service.impl;

import com.hotelapp.reservationservice.entity.Reservation;
import com.hotelapp.reservationservice.enums.ReservationStatus;
import com.hotelapp.reservationservice.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationStatusUpdater {

    private final ReservationRepository reservationRepository;

    public ReservationStatusUpdater(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Runs every hour
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void updateExpiredReservations() {
        LocalDate today = LocalDate.now();

        List<Reservation> confirmedToComplete = reservationRepository
                .findAllByStatusAndCheckOutDateBefore(ReservationStatus.CONFIRMED, today);
        confirmedToComplete.forEach(res -> res.setStatus(ReservationStatus.COMPLETED));

        List<Reservation> pendingToExpired = reservationRepository
                .findAllByStatusAndCheckOutDateBefore(ReservationStatus.PENDING, today);
        pendingToExpired.forEach(res -> res.setStatus(ReservationStatus.EXPIRED));

        reservationRepository.saveAll(confirmedToComplete);
        reservationRepository.saveAll(pendingToExpired);
    }
}