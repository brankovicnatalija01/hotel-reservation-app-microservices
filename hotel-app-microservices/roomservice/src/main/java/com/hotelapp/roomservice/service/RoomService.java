package com.hotelapp.roomservice.service;

import com.hotelapp.roomservice.dto.room.RoomRequestDTO;
import com.hotelapp.roomservice.dto.room.RoomResponseDTO;
import com.hotelapp.roomservice.dto.room.RoomSearchRequestDTO;

import java.util.List;

public interface RoomService {
    List<RoomResponseDTO> getAllRooms();
    RoomResponseDTO getRoomById(Long id);
    List<RoomResponseDTO> getRoomsByRoomType(String roomTypeName);
    List<RoomResponseDTO> getRoomsByAmenities(List<String> amenities);
    List<RoomResponseDTO> search(RoomSearchRequestDTO request);
    RoomResponseDTO createRoom(RoomRequestDTO dto);
    RoomResponseDTO updateRoom(Long roomId, RoomRequestDTO dto);
    void deleteRoom(Long roomId);
}