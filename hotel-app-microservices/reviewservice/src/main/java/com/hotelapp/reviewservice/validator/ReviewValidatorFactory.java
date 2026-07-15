package com.hotelapp.reviewservice.validator;

import com.hotelapp.reviewservice.client.ReservationServiceClient;
import com.hotelapp.reviewservice.enums.ValidationType;
import com.hotelapp.reviewservice.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewValidatorFactory {

    private final ReservationServiceClient reservationServiceClient;
    private final ReviewRepository reviewRepository;

    public ReviewValidator createValidator(ValidationType validationType) {
        return new ReviewValidator(reservationServiceClient, reviewRepository, validationType);
    }
}