package com.hotelapp.reservationservice.client.rest;

import com.hotelapp.reservationservice.client.UserServiceClient;
import com.hotelapp.sharedmodels.dto.UserSummaryDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
@Profile("rest")
public class RestUserServiceClient implements UserServiceClient {

    private final RestClient restClient;

    public RestUserServiceClient(@Qualifier("userRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public UserSummaryDTO getUserById(Long id) {
        try {
            return restClient.get()
                    .uri("/internal/users/{id}", id)
                    .retrieve()
                    .body(UserSummaryDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
    }
}