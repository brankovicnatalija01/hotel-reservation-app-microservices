package com.hotelapp.reservationservice.client;

import com.hotelapp.reservationservice.dto.RoomBookingSyncDTO;
import com.hotelapp.sharedmodels.dto.RoomSummaryDTO;

import java.time.LocalDate;

public interface RoomServiceClient {
    RoomSummaryDTO getRoomById(Long id);
    boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut);
    void syncBooking(RoomBookingSyncDTO dto);
}