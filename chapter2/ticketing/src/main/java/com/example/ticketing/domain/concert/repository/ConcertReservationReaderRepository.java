package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.AssignmentStatus;
import com.example.ticketing.domain.concert.entity.Reservation;

import java.util.Optional;

public interface ConcertReservationReaderRepository {
    Optional<Reservation> findNotAssignedBySeatId(Long seatId, AssignmentStatus status);

    int findVersion(Long reservationId);
}
