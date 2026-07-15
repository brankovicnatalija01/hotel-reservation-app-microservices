package com.hotelapp.roomservice.service;

import com.hotelapp.roomservice.dto.amenity.AmenityResponseDTO;

import java.util.List;

public interface AmenityService {
    List<AmenityResponseDTO> getAllAmenityNames();
}