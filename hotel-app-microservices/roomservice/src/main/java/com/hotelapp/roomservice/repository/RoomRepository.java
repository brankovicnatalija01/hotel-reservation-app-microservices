package com.hotelapp.roomservice.repository;

import com.hotelapp.roomservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    List<Room> findByRoomType_NameIgnoreCase(String name);

    boolean existsByRoomNumberAndProperty_Id(String roomNumber, Long propertyId);

    @Query("""
        SELECT r FROM Room r
        JOIN r.amenities a
        WHERE LOWER(a.name) IN :amenities
        GROUP BY r.id
        HAVING COUNT(DISTINCT a.id) = :amenityCount
    """)
    List<Room> findRoomsWithAllAmenitiesIgnoreCase(
            @Param("amenities") List<String> amenities,
            @Param("amenityCount") long amenityCount
    );
}