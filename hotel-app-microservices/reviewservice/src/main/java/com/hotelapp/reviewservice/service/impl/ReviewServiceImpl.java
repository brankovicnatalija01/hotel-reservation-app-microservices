package com.hotelapp.reviewservice.service.impl;

import com.hotelapp.reviewservice.client.ReservationServiceClient;
import com.hotelapp.reviewservice.dto.ReviewRequestDTO;
import com.hotelapp.reviewservice.dto.ReviewResponseDTO;
import com.hotelapp.reviewservice.entity.Review;
import com.hotelapp.reviewservice.enums.ValidationType;
import com.hotelapp.reviewservice.mapper.ReviewMapper;
import com.hotelapp.reviewservice.repository.ReviewRepository;
import com.hotelapp.reviewservice.service.ReviewService;
import com.hotelapp.reviewservice.validator.ReviewValidator;
import com.hotelapp.reviewservice.validator.ReviewValidatorFactory;
import com.hotelapp.sharedmodels.dto.ReservationSummaryDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewValidatorFactory reviewValidatorFactory;
    private final ReservationServiceClient reservationServiceClient;

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO dto) {
        ReviewValidator validator = reviewValidatorFactory.createValidator(ValidationType.CREATE);
        validator.validate(dto);

        // Fetch reservation snapshot data to store locally
        ReservationSummaryDTO reservation = reservationServiceClient.getReservationById(dto.getReservationId());

        Review review = reviewMapper.toEntity(dto);
        review.setReservationId(dto.getReservationId());
        review.setUserId(reservation.getUserId());
        review.setUserFirstName(reservation.getUserFirstName());
        review.setUserLastName(reservation.getUserLastName());
        review.setRoomId(reservation.getRoomId());
        review.setRoomNumber(reservation.getRoomNumber());
        review.setRoomTypeName(reservation.getRoomTypeName());
        review.setRoomTypeDescription(reservation.getRoomTypeDescription());

        Review saved = reviewRepository.save(review);
        return reviewMapper.toDto(saved);
    }

    @Override
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByUser(Long userId) {
        return reviewRepository.findAllByUserId(userId).stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByRoom(Long roomId) {
        return reviewRepository.findAllByRoomId(roomId).stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Override
    public ReviewResponseDTO updateReview(Long reviewId, ReviewRequestDTO dto) {
        ReviewValidator validator = reviewValidatorFactory.createValidator(ValidationType.UPDATE);
        validator.validate(dto);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with ID: " + reviewId));

        if (dto.getRating() != null) review.setRating(dto.getRating());
        if (dto.getComment() != null) review.setComment(dto.getComment());

        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new EntityNotFoundException("Review not found with ID: " + reviewId);
        }
        reviewRepository.deleteByIdCustom(reviewId);
    }
}