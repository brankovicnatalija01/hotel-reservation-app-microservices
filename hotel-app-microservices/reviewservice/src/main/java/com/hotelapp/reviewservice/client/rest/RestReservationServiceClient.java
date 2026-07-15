package com.hotelapp.reviewservice.client.rest;

import com.hotelapp.reviewservice.client.ReservationServiceClient;
import com.hotelapp.sharedmodels.dto.ReservationSummaryDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
@Profile("rest")
public class RestReservationServiceClient implements ReservationServiceClient {

    private final RestClient restClient;

    public RestReservationServiceClient(@Qualifier("reservationRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ReservationSummaryDTO getReservationById(Long id) {
        try {
            return restClient.get()
                    .uri("/internal/reservations/{id}", id)
                    .retrieve()
                    .body(ReservationSummaryDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new EntityNotFoundException("Reservation not found with ID: " + id);
        }
    }
}