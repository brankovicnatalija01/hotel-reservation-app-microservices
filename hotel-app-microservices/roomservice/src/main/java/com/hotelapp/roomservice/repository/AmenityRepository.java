package com.hotelapp.roomservice.repository;

import com.hotelapp.roomservice.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    @Query("SELECT a.name FROM Amenity a")
    List<String> findAllNames();
}