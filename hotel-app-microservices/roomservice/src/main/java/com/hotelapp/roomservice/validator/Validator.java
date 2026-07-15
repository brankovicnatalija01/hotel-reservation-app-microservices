package com.hotelapp.roomservice.validator;

import com.hotelapp.roomservice.exception.ValidationException;

public interface Validator<T> {
    void validate(T dto) throws ValidationException;
}