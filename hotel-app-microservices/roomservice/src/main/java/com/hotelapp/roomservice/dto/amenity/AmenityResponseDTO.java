package com.hotelapp.roomservice.dto.amenity;

import lombok.*;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @ToString
public class AmenityResponseDTO implements Serializable {
    private Long id;
    private String name;
}