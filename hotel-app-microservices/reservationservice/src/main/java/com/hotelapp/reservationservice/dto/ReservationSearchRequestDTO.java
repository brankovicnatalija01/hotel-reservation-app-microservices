package com.hotelapp.reservationservice.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ReservationSearchRequestDTO implements Serializable {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
    private String roomNumber;
    private String userFirstName;
    private String userLastName;
    private String fullName;
}