package com.hotelapp.reviewservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${services.reservation-service.url}")
    private String reservationServiceUrl;

    @Bean("reservationRestClient")
    public RestClient reservationRestClient() {
        return RestClient.builder().baseUrl(reservationServiceUrl).build();
    }
}