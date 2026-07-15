package com.hotelapp.roomservice.dto.room;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @ToString
public class RoomResponseDTO implements Serializable {
    private Long id;
    private String roomNumber;
    private BigDecimal pricePerNight;
    private String description;
    private Long propertyId;
    private String propertyName;
    private Long roomTypeId;
    private String roomTypeName;
    private String roomTypeDescription;
    private int roomTypeCapacity;
    private List<String> amenities;
    private List<String> imageUrls;
}