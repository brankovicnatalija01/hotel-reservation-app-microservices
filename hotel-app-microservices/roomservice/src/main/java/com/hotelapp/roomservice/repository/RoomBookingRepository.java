package com.hotelapp.roomservice.repository;

import com.hotelapp.roomservice.entity.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
    Optional<RoomBooking> findByReservationId(Long reservationId);
}