package com.hotelapp.userservice.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginResponse implements Serializable {
    private String token;
    private String email;
    private String role;
    private Long id;
}