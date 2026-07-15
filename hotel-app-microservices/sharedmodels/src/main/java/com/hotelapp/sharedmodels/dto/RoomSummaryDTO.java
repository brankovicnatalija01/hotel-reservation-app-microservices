package com.hotelapp.sharedmodels.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomSummaryDTO {
    private Long id;
    private String roomNumber;
    private BigDecimal pricePerNight;
    private String propertyName;
    private String roomTypeName;
    private int capacity;
    private String roomTypeDescription;
}