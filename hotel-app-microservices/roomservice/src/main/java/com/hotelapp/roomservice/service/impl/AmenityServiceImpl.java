package com.hotelapp.roomservice.service.impl;

import com.hotelapp.roomservice.dto.amenity.AmenityResponseDTO;
import com.hotelapp.roomservice.mapper.impl.AmenityMapper;
import com.hotelapp.roomservice.repository.AmenityRepository;
import com.hotelapp.roomservice.service.AmenityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository amenityRepository;
    private final AmenityMapper amenityMapper;

    @Override
    public List<AmenityResponseDTO> getAllAmenityNames() {
        return amenityRepository.findAll().stream().map(amenityMapper::toDto).toList();
    }
}