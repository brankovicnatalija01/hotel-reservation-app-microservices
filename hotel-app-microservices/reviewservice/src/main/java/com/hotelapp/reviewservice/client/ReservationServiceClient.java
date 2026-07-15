package com.hotelapp.reviewservice.client;

import com.hotelapp.sharedmodels.dto.ReservationSummaryDTO;

public interface ReservationServiceClient {
    ReservationSummaryDTO getReservationById(Long id);
}