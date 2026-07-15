package com.hotelapp.reviewservice.repository;

import com.hotelapp.reviewservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByReservationId(Long reservationId);

    List<Review> findAllByUserId(Long userId);

    List<Review> findAllByRoomId(Long roomId);

    @Modifying
    @Query("DELETE FROM Review r WHERE r.id = :id")
    void deleteByIdCustom(@Param("id") Long id);
}