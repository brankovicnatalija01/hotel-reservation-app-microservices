package com.hotelapp.reviewservice.service;

import com.hotelapp.reviewservice.dto.ReviewRequestDTO;
import com.hotelapp.reviewservice.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    ReviewResponseDTO createReview(ReviewRequestDTO dto);
    List<ReviewResponseDTO> getAllReviews();
    List<ReviewResponseDTO> getReviewsByUser(Long userId);
    List<ReviewResponseDTO> getReviewsByRoom(Long roomId);
    ReviewResponseDTO updateReview(Long reviewId, ReviewRequestDTO dto);
    void deleteReview(Long reviewId);
}