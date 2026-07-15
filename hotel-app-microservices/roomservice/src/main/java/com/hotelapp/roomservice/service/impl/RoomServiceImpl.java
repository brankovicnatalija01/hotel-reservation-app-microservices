package com.hotelapp.roomservice.service.impl;

import com.hotelapp.roomservice.dto.room.RoomRequestDTO;
import com.hotelapp.roomservice.dto.room.RoomResponseDTO;
import com.hotelapp.roomservice.dto.room.RoomSearchRequestDTO;
import com.hotelapp.roomservice.entity.Room;
import com.hotelapp.roomservice.entity.RoomImage;
import com.hotelapp.roomservice.enums.ValidationType;
import com.hotelapp.roomservice.mapper.impl.RoomMapper;
import com.hotelapp.roomservice.repository.*;
import com.hotelapp.roomservice.service.RoomService;
import com.hotelapp.roomservice.specification.RoomSpecification;
import com.hotelapp.roomservice.validator.RoomValidator;
import com.hotelapp.roomservice.validator.factory.RoomValidatorFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PropertyRepository propertyRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final AmenityRepository amenityRepository;
    private final RoomMapper roomMapper;
    private final RoomValidatorFactory roomValidatorFactory;

    private static final String ROOM_NOT_FOUND = "Room not found with ID: ";

    @Override
    public List<RoomResponseDTO> getAllRooms() {
        return roomRepository.findAll().stream().map(roomMapper::toDto).toList();
    }

    @Override
    public RoomResponseDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ROOM_NOT_FOUND + id));
        return roomMapper.toDto(room);
    }

    @Override
    public List<RoomResponseDTO> getRoomsByRoomType(String roomTypeName) {
        return roomRepository.findByRoomType_NameIgnoreCase(roomTypeName.trim())
                .stream().map(roomMapper::toDto).toList();
    }

    @Override
    public List<RoomResponseDTO> getRoomsByAmenities(List<String> amenities) {
        List<String> normalized = amenities.stream().map(String::toLowerCase).toList();
        return roomRepository.findRoomsWithAllAmenitiesIgnoreCase(normalized, normalized.size())
                .stream().map(roomMapper::toDto).toList();
    }

    @Override
    public List<RoomResponseDTO> search(RoomSearchRequestDTO request) {
        return roomRepository.findAll(RoomSpecification.filter(request))
                .stream().map(roomMapper::toDto).toList();
    }

    @Override
    public RoomResponseDTO createRoom(RoomRequestDTO dto) {
        RoomValidator validator = roomValidatorFactory.createValidator(ValidationType.CREATE);
        validator.validate(dto);

        Room room = roomMapper.toEntity(dto);
        room.setProperty(propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Property not found")));
        room.setRoomType(roomTypeRepository.findById(dto.getRoomTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Room type not found")));

        if (dto.getAmenityIds() != null && !dto.getAmenityIds().isEmpty()) {
            room.setAmenities(new HashSet<>(amenityRepository.findAllById(dto.getAmenityIds())));
        }
        if (dto.getImageUrls() != null) {
            for (String url : dto.getImageUrls()) {
                RoomImage img = new RoomImage();
                img.setUrl(url);
                img.setRoom(room);
                room.getImages().add(img);
            }
        }

        roomRepository.flush();
        return roomMapper.toDto(roomRepository.save(room));
    }

    @Override
    public RoomResponseDTO updateRoom(Long roomId, RoomRequestDTO dto) {
        RoomValidator validator = roomValidatorFactory.createValidator(ValidationType.UPDATE);
        validator.validate(dto);

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(ROOM_NOT_FOUND + roomId));

        if (dto.getPropertyId() != null) room.setProperty(propertyRepository.findById(dto.getPropertyId()).get());
        if (dto.getRoomTypeId() != null) room.setRoomType(roomTypeRepository.findById(dto.getRoomTypeId()).get());
        if (dto.getRoomNumber() != null) room.setRoomNumber(dto.getRoomNumber());
        if (dto.getPricePerNight() != null) room.setPricePerNight(dto.getPricePerNight());
        if (dto.getDescription() != null) room.setDescription(dto.getDescription());
        if (dto.getAmenityIds() != null) room.setAmenities(new HashSet<>(amenityRepository.findAllById(dto.getAmenityIds())));
        if (dto.getImageUrls() != null) {
            room.getImages().clear();
            dto.getImageUrls().forEach(url -> {
                RoomImage img = new RoomImage();
                img.setUrl(url);
                img.setRoom(room);
                room.getImages().add(img);
            });
        }

        return roomMapper.toDto(roomRepository.save(room));
    }

    @Override
    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(ROOM_NOT_FOUND + roomId));
        roomRepository.delete(room);
    }
}