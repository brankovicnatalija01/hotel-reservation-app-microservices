package com.hotelapp.roomservice.mapper.impl;

import com.hotelapp.roomservice.dto.amenity.AmenityResponseDTO;
import com.hotelapp.roomservice.entity.Amenity;
import com.hotelapp.roomservice.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class AmenityMapper implements Mapper<Void, AmenityResponseDTO, Amenity> {

    @Override
    public Amenity toEntity(Void dto) { return null; }

    @Override
    public AmenityResponseDTO toDto(Amenity amenity) {
        AmenityResponseDTO dto = new AmenityResponseDTO();
        dto.setId(amenity.getId());
        dto.setName(amenity.getName());
        return dto;
    }
}