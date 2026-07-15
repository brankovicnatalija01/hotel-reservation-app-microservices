package com.hotelapp.roomservice.dto.room;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @ToString
public class RoomRequestDTO implements Serializable {
    private String roomNumber;
    private BigDecimal pricePerNight;
    private String description;
    private Long propertyId;
    private Long roomTypeId;
    private List<Long> amenityIds;
    private List<String> imageUrls;
}