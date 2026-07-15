package com.hotelapp.roomservice.controller;

import com.hotelapp.roomservice.dto.room.RoomBookingSyncDTO;
import com.hotelapp.roomservice.entity.Room;
import com.hotelapp.roomservice.entity.RoomBooking;
import com.hotelapp.roomservice.enums.BookingStatus;
import com.hotelapp.roomservice.repository.RoomBookingRepository;
import com.hotelapp.roomservice.repository.RoomRepository;
import com.hotelapp.sharedmodels.dto.RoomSummaryDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

// Internal endpoints used only by other microservices, not exposed to frontend clients.
@RestController
@RequestMapping("/internal/rooms")
@RequiredArgsConstructor
public class InternalRoomController {

    private final RoomRepository roomRepository;
    private final RoomBookingRepository roomBookingRepository;

    @GetMapping("/{id}")
    public RoomSummaryDTO getRoomById(@PathVariable Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + id));
        return new RoomSummaryDTO(
                room.getId(),
                room.getRoomNumber(),
                room.getPricePerNight(),
                room.getProperty().getName(),
                room.getRoomType().getName(),
                room.getRoomType().getCapacity(),
                room.getRoomType().getDescription()
        );
    }

    @GetMapping("/{id}/availability")
    public boolean checkAvailability(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        return roomBookingRepository.findAll().stream()
                .filter(b -> b.getRoom().getId().equals(id))
                .filter(b -> b.getStatus() == BookingStatus.PENDING || b.getStatus() == BookingStatus.CONFIRMED)
                .noneMatch(b -> b.getCheckInDate().isBefore(checkOut) && b.getCheckOutDate().isAfter(checkIn));
    }

    // Called by reservation-service whenever a reservation is created or its status changes.
    @PostMapping("/bookings/sync")
    public ResponseEntity<Void> syncBooking(@RequestBody RoomBookingSyncDTO dto) {
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + dto.getRoomId()));

        RoomBooking booking = roomBookingRepository.findByReservationId(dto.getReservationId())
                .orElse(new RoomBooking());

        booking.setReservationId(dto.getReservationId());
        booking.setRoom(room);
        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setStatus(BookingStatus.valueOf(dto.getStatus()));

        roomBookingRepository.save(booking);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}