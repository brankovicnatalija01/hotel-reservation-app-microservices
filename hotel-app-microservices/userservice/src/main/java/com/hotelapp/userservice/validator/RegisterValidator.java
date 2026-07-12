package com.hotelapp.userservice.validator;

import com.hotelapp.userservice.dto.RegisterRequestDTO;
import com.hotelapp.userservice.exception.ValidationException;
import com.hotelapp.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class RegisterValidator implements Validator<RegisterRequestDTO> {

    private final UserRepository userRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{8,15}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]{2,}$");

    @Override
    public void validate(RegisterRequestDTO dto) throws ValidationException {
        validateEmail(dto.getEmail());
        validatePassword(dto.getPassword());
        validateName(dto.getFirstName(), "First name");
        validateName(dto.getLastName(), "Last name");
        validatePhone(dto.getPhone());
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) throw new ValidationException("Email is required");
        String trimmed = email.trim();
        if (!EMAIL_PATTERN.matcher(trimmed).matches()) throw new ValidationException("Email format is invalid");
        if (userRepository.existsByEmail(trimmed)) throw new ValidationException("Email already exists");
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 6) throw new ValidationException("Password must have at least 6 characters");
        if (!password.matches(".*[A-Za-z].*")) throw new ValidationException("Password must contain at least one letter");
        if (!password.matches(".*\\d.*")) throw new ValidationException("Password must contain at least one number");
    }

    private void validateName(String name, String fieldName) {
        if (name == null || name.isBlank()) throw new ValidationException(fieldName + " is required");
        if (!NAME_PATTERN.matcher(name).matches()) throw new ValidationException(fieldName + " must contain only letters and be at least 2 characters long");
    }

    private void validatePhone(String phone) {
        if (phone == null || phone.isBlank()) throw new ValidationException("Phone number is required");
        if (!PHONE_PATTERN.matcher(phone).matches()) throw new ValidationException("Phone number format is invalid");
    }
}