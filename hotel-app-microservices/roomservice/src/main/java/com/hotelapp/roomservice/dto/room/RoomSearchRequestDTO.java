package com.hotelapp.roomservice.dto.room;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @ToString
public class RoomSearchRequestDTO implements Serializable {
    private Integer capacity;
    private List<String> amenities;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private LocalDate checkIn;
    private LocalDate checkOut;
}