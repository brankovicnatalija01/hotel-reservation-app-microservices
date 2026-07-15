package com.hotelapp.roomservice.controller;

import com.hotelapp.roomservice.dto.room.RoomRequestDTO;
import com.hotelapp.roomservice.dto.room.RoomResponseDTO;
import com.hotelapp.roomservice.dto.room.RoomSearchRequestDTO;
import com.hotelapp.roomservice.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/filter/type")
    public ResponseEntity<List<RoomResponseDTO>> filterByRoomType(@RequestParam String type) {
        return ResponseEntity.ok(roomService.getRoomsByRoomType(type));
    }

    @GetMapping("/filter/amenities")
    public ResponseEntity<List<RoomResponseDTO>> filterByAmenities(@RequestParam List<String> amenities) {
        return ResponseEntity.ok(roomService.getRoomsByAmenities(amenities));
    }

    @PostMapping("/search")
    public ResponseEntity<List<RoomResponseDTO>> searchRooms(@RequestBody RoomSearchRequestDTO request) {
        return ResponseEntity.ok(roomService.search(request));
    }

    @PostMapping
    public ResponseEntity<RoomResponseDTO> createRoom(@RequestBody RoomRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> updateRoom(@PathVariable Long id, @RequestBody RoomRequestDTO dto) {
        return ResponseEntity.ok(roomService.updateRoom(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}