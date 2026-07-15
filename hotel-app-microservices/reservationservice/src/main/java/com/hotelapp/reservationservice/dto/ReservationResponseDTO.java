package com.hotelapp.reservationservice.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ReservationResponseDTO implements Serializable {
    private Long reservationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
    private BigDecimal totalPrice;
    private Long roomId;
    private String roomNumber;
    private String roomName;
    private String roomDescription;
    private Long userId;
    private String userFirstName;
    private String userLastName;
}