package com.hotelapp.reviewservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;

    @Column(length = 1000)
    private String comment;

    // Cross-service reference stored as plain ID (no JPA relation)
    private Long reservationId;

    // Snapshot fields captured at review creation time
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private Long roomId;
    private String roomNumber;
    private String roomTypeName;
    private String roomTypeDescription;
}