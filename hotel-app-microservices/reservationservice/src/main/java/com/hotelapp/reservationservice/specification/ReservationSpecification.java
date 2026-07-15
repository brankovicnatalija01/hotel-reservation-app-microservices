package com.hotelapp.reservationservice.specification;

import com.hotelapp.reservationservice.dto.ReservationSearchRequestDTO;
import com.hotelapp.reservationservice.entity.Reservation;
import com.hotelapp.reservationservice.enums.ReservationStatus;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ReservationSpecification {

    public static Specification<Reservation> filter(ReservationSearchRequestDTO req) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Full name filter using denormalized snapshot fields
            if (req.getFullName() != null && !req.getFullName().isBlank()) {
                Expression<String> fullNameExpr = cb.concat(
                        cb.lower(root.get("userFirstName")),
                        cb.concat(" ", cb.lower(root.get("userLastName"))));
                predicates.add(cb.like(fullNameExpr, "%" + req.getFullName().toLowerCase() + "%"));
            } else {
                if (req.getUserFirstName() != null && !req.getUserFirstName().isBlank()) {
                    predicates.add(cb.like(cb.lower(root.get("userFirstName")),
                            "%" + req.getUserFirstName().toLowerCase() + "%"));
                }
                if (req.getUserLastName() != null && !req.getUserLastName().isBlank()) {
                    predicates.add(cb.like(cb.lower(root.get("userLastName")),
                            "%" + req.getUserLastName().toLowerCase() + "%"));
                }
            }

            if (req.getStatus() != null && !req.getStatus().isBlank()) {
                predicates.add(cb.equal(root.get("status"), ReservationStatus.valueOf(req.getStatus())));
            }

            if (req.getRoomNumber() != null && !req.getRoomNumber().isBlank()) {
                predicates.add(cb.equal(root.get("roomNumber"), req.getRoomNumber()));
            }

            if (req.getCheckInDate() != null) {
                predicates.add(cb.lessThan(root.get("checkOutDate"), req.getCheckInDate()).not());
            }
            if (req.getCheckOutDate() != null) {
                predicates.add(cb.greaterThan(root.get("checkInDate"), req.getCheckOutDate()).not());
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}