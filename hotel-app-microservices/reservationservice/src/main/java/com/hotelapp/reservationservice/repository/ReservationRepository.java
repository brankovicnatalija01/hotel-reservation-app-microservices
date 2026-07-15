package com.hotelapp.reservationservice.repository;

import com.hotelapp.reservationservice.entity.Reservation;
import com.hotelapp.reservationservice.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

    @Query("""
        SELECT CASE WHEN COUNT(r.id) > 0 THEN true ELSE false END
        FROM Reservation r
        WHERE r.roomId = :roomId
          AND r.status IN :activeStatuses
          AND r.checkInDate < :checkOutDate
          AND r.checkOutDate > :checkInDate
    """)
    boolean isRoomOccupied(
            @Param("roomId") Long roomId,
            @Param("activeStatuses") List<ReservationStatus> activeStatuses,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findAllByStatusAndCheckOutDateBefore(ReservationStatus status, LocalDate date);
}