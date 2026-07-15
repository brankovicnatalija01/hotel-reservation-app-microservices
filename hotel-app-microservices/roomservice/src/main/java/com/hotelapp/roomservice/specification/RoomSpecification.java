package com.hotelapp.roomservice.specification;

import com.hotelapp.roomservice.dto.room.RoomSearchRequestDTO;
import com.hotelapp.roomservice.entity.Amenity;
import com.hotelapp.roomservice.entity.Room;
import com.hotelapp.roomservice.entity.RoomBooking;
import com.hotelapp.roomservice.enums.BookingStatus;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RoomSpecification {

    public static Specification<Room> filter(RoomSearchRequestDTO req) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (req.getCapacity() != null && req.getCapacity() > 0) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("roomType").get("capacity"), req.getCapacity()));
            }

            if (req.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("pricePerNight"), req.getMinPrice()));
            }

            if (req.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("pricePerNight"), req.getMaxPrice()));
            }

            if (req.getAmenities() != null && !req.getAmenities().isEmpty()) {
                Join<Room, Amenity> amenityJoin = root.join("amenities");
                Expression<String> normalized = cb.lower(cb.function(
                        "replace", String.class, amenityJoin.get("name"), cb.literal(" "), cb.literal("")));
                predicates.add(normalized.in(
                        req.getAmenities().stream().map(a -> a.toLowerCase().replace(" ", "")).toList()));
                query.groupBy(root.get("id"));
                query.having(cb.equal(cb.countDistinct(amenityJoin.get("id")), (long) req.getAmenities().size()));
            }

            // Availability check uses local RoomBooking table instead of Reservation from another service.
            if (req.getCheckIn() != null && req.getCheckOut() != null) {
                Subquery<Long> sub = query.subquery(Long.class);
                Root<RoomBooking> booking = sub.from(RoomBooking.class);
                sub.select(booking.get("room").get("id"))
                        .where(cb.and(
                                cb.lessThan(booking.get("checkInDate"), req.getCheckOut()),
                                cb.greaterThan(booking.get("checkOutDate"), req.getCheckIn()),
                                booking.get("status").in(BookingStatus.PENDING, BookingStatus.CONFIRMED)
                        ));
                predicates.add(cb.not(root.get("id").in(sub)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}