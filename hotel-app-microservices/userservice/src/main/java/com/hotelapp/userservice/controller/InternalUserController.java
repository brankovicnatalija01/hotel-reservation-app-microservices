package com.hotelapp.userservice.controller;

import com.hotelapp.sharedmodels.dto.UserSummaryDTO;
import com.hotelapp.userservice.entity.User;
import com.hotelapp.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// Internal endpoint used only by other microservices, not exposed to frontend clients.
@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public UserSummaryDTO getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        return new UserSummaryDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),  user.getPhone() );
    }
}