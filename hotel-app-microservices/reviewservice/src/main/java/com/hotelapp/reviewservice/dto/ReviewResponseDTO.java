package com.hotelapp.reviewservice.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ReviewResponseDTO implements Serializable {
    private Long reviewId;
    private Integer rating;
    private String comment;
    private Long reservationId;
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private Long roomId;
    private String roomName;
    private String roomType;
    private String roomTypeDescription;
}