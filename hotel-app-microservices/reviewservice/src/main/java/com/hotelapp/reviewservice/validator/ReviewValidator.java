package com.hotelapp.reviewservice.validator;

import com.hotelapp.reviewservice.client.ReservationServiceClient;
import com.hotelapp.reviewservice.dto.ReviewRequestDTO;
import com.hotelapp.reviewservice.enums.ValidationType;
import com.hotelapp.reviewservice.exception.ValidationException;
import com.hotelapp.reviewservice.repository.ReviewRepository;
import com.hotelapp.sharedmodels.dto.ReservationSummaryDTO;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class ReviewValidator {

    private final ReservationServiceClient reservationServiceClient;
    private final ReviewRepository reviewRepository;
    private final ValidationType validationType;

    public void validate(ReviewRequestDTO dto) throws ValidationException {
        if (dto.getRating() != null && (dto.getRating() < 1 || dto.getRating() > 10)) {
            throw new ValidationException("Rating must be between 1 and 10");
        }

        if (dto.getComment() != null && dto.getComment().length() > 300) {
            throw new ValidationException("Comment cannot exceed 300 characters");
        }

        if (validationType == ValidationType.CREATE) {
            validateCreate(dto);
        } else if (validationType == ValidationType.UPDATE) {
            validateUpdate(dto);
        }
    }

    private void validateUpdate(ReviewRequestDTO dto) {
        if (dto.getRating() == null && dto.getComment() == null) {
            throw new ValidationException("At least one field (rating or comment) must be provided for update");
        }
        if (dto.getReservationId() != null) {
            throw new ValidationException("Reservation ID cannot be changed on update");
        }
    }

    private void validateCreate(ReviewRequestDTO dto) {
        if (dto.getRating() == null) {
            throw new ValidationException("Rating is required");
        }
        if (dto.getComment() == null || dto.getComment().isBlank()) {
            throw new ValidationException("Comment is required");
        }

        // Fetch reservation from reservation-service (throws EntityNotFoundException if missing)
        ReservationSummaryDTO reservation = reservationServiceClient.getReservationById(dto.getReservationId());

        boolean isConfirmed = "CONFIRMED".equals(reservation.getStatus());
        boolean checkoutPassed = !reservation.getCheckOutDate().isAfter(LocalDate.now());

        if (!isConfirmed && !checkoutPassed) {
            throw new ValidationException("Cannot review a reservation that is not finished or not confirmed");
        }

        if (reviewRepository.existsByReservationId(dto.getReservationId())) {
            throw new ValidationException("You have already submitted a review for this reservation");
        }
    }
}