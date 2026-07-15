package com.hotelapp.reservationservice.client;

import com.hotelapp.sharedmodels.dto.UserSummaryDTO;

public interface UserServiceClient {
    UserSummaryDTO getUserById(Long id);
}