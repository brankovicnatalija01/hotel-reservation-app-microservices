package com.hotelapp.reservationservice.client.rest;

import com.hotelapp.reservationservice.client.RoomServiceClient;
import com.hotelapp.reservationservice.dto.RoomBookingSyncDTO;
import com.hotelapp.sharedmodels.dto.RoomSummaryDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

@Service
@Profile("rest")
public class RestRoomServiceClient implements RoomServiceClient {

    private final RestClient restClient;

    public RestRoomServiceClient(@Qualifier("roomRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public RoomSummaryDTO getRoomById(Long id) {
        try {
            return restClient.get()
                    .uri("/internal/rooms/{id}", id)
                    .retrieve()
                    .body(RoomSummaryDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new EntityNotFoundException("Room not found with ID: " + id);
        }
    }

    @Override
    public boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        Boolean available = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/internal/rooms/{id}/availability")
                        .queryParam("checkIn", checkIn)
                        .queryParam("checkOut", checkOut)
                        .build(roomId))
                .retrieve()
                .body(Boolean.class);
        return Boolean.TRUE.equals(available);
    }

    @Override
    public void syncBooking(RoomBookingSyncDTO dto) {
        restClient.post()
                .uri("/internal/rooms/bookings/sync")
                .body(dto)
                .retrieve()
                .toBodilessEntity();
    }
}