package com.hotelapp.roomservice.validator.factory;

import com.hotelapp.roomservice.enums.ValidationType;
import com.hotelapp.roomservice.repository.AmenityRepository;
import com.hotelapp.roomservice.repository.PropertyRepository;
import com.hotelapp.roomservice.repository.RoomRepository;
import com.hotelapp.roomservice.repository.RoomTypeRepository;
import com.hotelapp.roomservice.validator.RoomValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoomValidatorFactory {

    private final PropertyRepository propertyRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final AmenityRepository amenityRepository;
    private final RoomRepository roomRepository;

    public RoomValidator createValidator(ValidationType type) {
        return new RoomValidator(type, propertyRepository, roomTypeRepository, amenityRepository, roomRepository);
    }
}