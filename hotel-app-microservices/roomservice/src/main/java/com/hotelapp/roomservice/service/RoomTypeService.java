package com.hotelapp.roomservice.service;

import com.hotelapp.roomservice.dto.roomtype.RoomTypeResponseDTO;

import java.util.List;

public interface RoomTypeService {
    List<RoomTypeResponseDTO> getAllRoomTypes();
}