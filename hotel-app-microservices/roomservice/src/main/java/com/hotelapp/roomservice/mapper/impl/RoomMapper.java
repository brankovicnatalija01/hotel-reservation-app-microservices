package com.hotelapp.roomservice.mapper.impl;

import com.hotelapp.roomservice.dto.room.RoomRequestDTO;
import com.hotelapp.roomservice.dto.room.RoomResponseDTO;
import com.hotelapp.roomservice.entity.Amenity;
import com.hotelapp.roomservice.entity.Room;
import com.hotelapp.roomservice.entity.RoomImage;
import com.hotelapp.roomservice.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper implements Mapper<RoomRequestDTO, RoomResponseDTO, Room> {

    @Override
    public Room toEntity(RoomRequestDTO dto) {
        if (dto == null) return null;
        Room room = new Room();
        room.setRoomNumber(dto.getRoomNumber());
        room.setPricePerNight(dto.getPricePerNight());
        room.setDescription(dto.getDescription());
        return room;
    }

    @Override
    public RoomResponseDTO toDto(Room room) {
        if (room == null) return null;
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setPricePerNight(room.getPricePerNight());
        dto.setDescription(room.getDescription());
        dto.setPropertyId(room.getProperty().getId());
        dto.setPropertyName(room.getProperty().getName());
        dto.setRoomTypeId(room.getRoomType().getId());
        dto.setRoomTypeName(room.getRoomType().getName());
        dto.setRoomTypeDescription(room.getRoomType().getDescription());
        dto.setRoomTypeCapacity(room.getRoomType().getCapacity());
        dto.setAmenities(room.getAmenities().stream().map(Amenity::getName).toList());
        if (room.getImages() != null) {
            dto.setImageUrls(room.getImages().stream().map(RoomImage::getUrl).toList());
        }
        return dto;
    }
}