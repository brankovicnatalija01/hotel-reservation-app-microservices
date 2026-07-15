package com.hotelapp.roomservice.mapper.impl;

import com.hotelapp.roomservice.dto.roomtype.RoomTypeResponseDTO;
import com.hotelapp.roomservice.entity.RoomType;
import com.hotelapp.roomservice.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class RoomTypeMapper implements Mapper<Void, RoomTypeResponseDTO, RoomType> {

    @Override
    public RoomType toEntity(Void dto) { return null; }

    @Override
    public RoomTypeResponseDTO toDto(RoomType roomType) {
        RoomTypeResponseDTO dto = new RoomTypeResponseDTO();
        dto.setId(roomType.getId());
        dto.setName(roomType.getName());
        dto.setDescription(roomType.getDescription());
        dto.setCapacity(roomType.getCapacity());
        return dto;
    }
}