package com.hotelapp.roomservice.dto.room;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

// Sent by reservation-service to keep room-service availability data in sync.
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @ToString
public class RoomBookingSyncDTO implements Serializable {
    private Long reservationId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
}