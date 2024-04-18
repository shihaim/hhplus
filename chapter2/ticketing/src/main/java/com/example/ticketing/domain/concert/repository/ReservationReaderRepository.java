package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.AssignmentStatus;
import com.example.ticketing.domain.concert.entity.Reservation;

import java.util.Optional;

public interface ReservationReaderRepository {
    Optional<Reservation> findNotAssignedBySeatId(Long seatId, AssignmentStatus status);

    Optional<Reservation> findAssignedByUserUUID(String userUUID, int token);
}
