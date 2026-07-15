package com.hotelapp.reviewservice.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ReviewRequestDTO implements Serializable {
    private Integer rating;
    private String comment;
    private Long reservationId;
}