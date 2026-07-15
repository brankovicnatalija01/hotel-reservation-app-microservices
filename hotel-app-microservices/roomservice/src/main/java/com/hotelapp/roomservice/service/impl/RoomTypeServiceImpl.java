package com.hotelapp.roomservice.service.impl;

import com.hotelapp.roomservice.dto.roomtype.RoomTypeResponseDTO;
import com.hotelapp.roomservice.mapper.impl.RoomTypeMapper;
import com.hotelapp.roomservice.repository.RoomTypeRepository;
import com.hotelapp.roomservice.service.RoomTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final RoomTypeMapper roomTypeMapper;

    @Override
    public List<RoomTypeResponseDTO> getAllRoomTypes() {
        return roomTypeRepository.findAll().stream().map(roomTypeMapper::toDto).toList();
    }
}