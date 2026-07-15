package com.hotelapp.reservationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${services.user-service.url}")
    private String userServiceUrl;

    @Value("${services.room-service.url}")
    private String roomServiceUrl;

    @Bean("userRestClient")
    public RestClient userRestClient() {
        return RestClient.builder().baseUrl(userServiceUrl).build();
    }

    @Bean("roomRestClient")
    public RestClient roomRestClient() {
        return RestClient.builder().baseUrl(roomServiceUrl).build();
    }
}