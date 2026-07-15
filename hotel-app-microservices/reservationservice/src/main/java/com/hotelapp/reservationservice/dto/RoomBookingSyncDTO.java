package com.hotelapp.reservationservice.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

// Sent to room-service to keep its availability data (RoomBooking table) in sync.
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @ToString
public class RoomBookingSyncDTO implements Serializable {
    private Long reservationId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
}