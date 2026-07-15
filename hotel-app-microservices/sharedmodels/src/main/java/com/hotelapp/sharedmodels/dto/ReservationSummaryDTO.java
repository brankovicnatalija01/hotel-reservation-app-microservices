package com.hotelapp.sharedmodels.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSummaryDTO {
    private Long id;
    private Long userId;
    private Long roomId;
    private String userFirstName;
    private String userLastName;
    private String roomNumber;
    private String roomTypeName;
    private String roomTypeDescription;
    private String status;
    private LocalDate checkOutDate;
}