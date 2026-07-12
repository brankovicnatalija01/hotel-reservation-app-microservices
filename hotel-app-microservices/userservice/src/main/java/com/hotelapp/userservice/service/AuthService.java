package com.hotelapp.userservice.service;

import com.hotelapp.userservice.dto.LoginRequest;
import com.hotelapp.userservice.dto.LoginResponse;
import com.hotelapp.userservice.dto.RegisterRequestDTO;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    void register(RegisterRequestDTO dto);
}