package com.hotelapp.userservice.validator;

import com.hotelapp.userservice.exception.ValidationException;

public interface Validator<T> {
    void validate(T dto) throws ValidationException;
}