package com.hotelapp.reservationservice.service;

import com.hotelapp.reservationservice.dto.ReservationRequestDTO;
import com.hotelapp.reservationservice.dto.ReservationResponseDTO;
import com.hotelapp.reservationservice.dto.ReservationSearchRequestDTO;

import java.util.List;

public interface ReservationService {
    ReservationResponseDTO createReservation(ReservationRequestDTO dto);
    ReservationResponseDTO getReservationById(Long reservationId);
    List<ReservationResponseDTO> getReservationsByUserId(Long userId);
    List<ReservationResponseDTO> getAllReservations();
    void deleteReservation(Long reservationId);
    ReservationResponseDTO cancelReservation(Long reservationId);
    ReservationResponseDTO approveReservation(Long reservationId);
    ReservationResponseDTO rejectReservation(Long reservationId);
    List<ReservationResponseDTO> search(ReservationSearchRequestDTO request);
}