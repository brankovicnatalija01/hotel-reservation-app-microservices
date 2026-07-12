package com.hotelapp.userservice.service.impl;

import com.hotelapp.userservice.dto.LoginRequest;
import com.hotelapp.userservice.dto.LoginResponse;
import com.hotelapp.userservice.dto.RegisterRequestDTO;
import com.hotelapp.userservice.entity.Role;
import com.hotelapp.userservice.entity.User;
import com.hotelapp.userservice.repository.RoleRepository;
import com.hotelapp.userservice.repository.UserRepository;
import com.hotelapp.userservice.security.JwtUtils;
import com.hotelapp.userservice.security.UserDetailsImpl;
import com.hotelapp.userservice.service.AuthService;
import com.hotelapp.userservice.validator.RegisterValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterValidator registerValidator;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = Optional.ofNullable(authentication.getPrincipal())
                .filter(UserDetailsImpl.class::isInstance)
                .map(UserDetailsImpl.class::cast)
                .orElseThrow(() -> new RuntimeException("Could not retrieve user details from context"));

        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        return new LoginResponse(jwt, userDetails.getUsername(), role, userDetails.getId());
    }

    @Override
    public void register(RegisterRequestDTO dto) {
        registerValidator.validate(dto);
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("ROLE_USER not found"));

        User user = new User();
        user.setFirstName(dto.getFirstName().trim());
        user.setLastName(dto.getLastName().trim());
        user.setEmail(dto.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone().trim());
        user.setRole(userRole);

        userRepository.save(user);
    }
}