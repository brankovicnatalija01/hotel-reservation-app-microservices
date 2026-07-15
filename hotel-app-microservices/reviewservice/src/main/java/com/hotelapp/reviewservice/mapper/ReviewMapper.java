package com.hotelapp.reviewservice.mapper;

import com.hotelapp.reviewservice.dto.ReviewRequestDTO;
import com.hotelapp.reviewservice.dto.ReviewResponseDTO;
import com.hotelapp.reviewservice.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review toEntity(ReviewRequestDTO dto) {
        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return review;
    }

    public ReviewResponseDTO toDto(Review review) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setReviewId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setReservationId(review.getReservationId());
        dto.setUserId(review.getUserId());
        dto.setUserFirstName(review.getUserFirstName());
        dto.setUserLastName(review.getUserLastName());
        dto.setRoomId(review.getRoomId());
        dto.setRoomName(review.getRoomNumber());
        dto.setRoomType(review.getRoomTypeName());
        dto.setRoomTypeDescription(review.getRoomTypeDescription());
        return dto;
    }
}