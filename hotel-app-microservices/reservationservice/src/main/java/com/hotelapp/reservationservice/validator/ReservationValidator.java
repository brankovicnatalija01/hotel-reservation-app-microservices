package com.hotelapp.reservationservice.validator;

import com.hotelapp.reservationservice.client.RoomServiceClient;
import com.hotelapp.reservationservice.client.UserServiceClient;
import com.hotelapp.reservationservice.dto.ReservationRequestDTO;
import com.hotelapp.reservationservice.exception.ValidationException;
import com.hotelapp.sharedmodels.dto.RoomSummaryDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Component
public class ReservationValidator {

    private final UserServiceClient userServiceClient;
    private final RoomServiceClient roomServiceClient;

    public void validate(ReservationRequestDTO dto) throws ValidationException {
        // Throws EntityNotFoundException if user does not exist
        userServiceClient.getUserById(dto.getUserId());

        // Throws EntityNotFoundException if room does not exist
        RoomSummaryDTO room = roomServiceClient.getRoomById(dto.getRoomId());

        if (dto.getCheckInDate() == null || dto.getCheckOutDate() == null) {
            throw new ValidationException("Check-in and check-out dates are required");
        }

        LocalDate today = LocalDate.now();

        if (dto.getCheckInDate().isBefore(today)) {
            throw new ValidationException("Check-in date must be today or in the future");
        }

        if (!dto.getCheckOutDate().isAfter(today)) {
            throw new ValidationException("Check-out date must be in the future");
        }

        if (dto.getCheckOutDate().isBefore(dto.getCheckInDate())) {
            throw new ValidationException("Check-out date must be after check-in date");
        }

        long nights = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        if (nights <= 0) {
            throw new ValidationException("Reservation must be at least 1 night");
        }

        boolean available = roomServiceClient.isRoomAvailable(dto.getRoomId(), dto.getCheckInDate(), dto.getCheckOutDate());
        if (!available) {
            throw new ValidationException("Room " + room.getRoomNumber() + " is already booked for the selected dates");
        }
    }
}