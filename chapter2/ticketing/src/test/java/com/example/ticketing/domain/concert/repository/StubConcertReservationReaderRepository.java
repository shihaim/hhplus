package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.AssignmentStatus;
import com.example.ticketing.domain.concert.entity.Reservation;

import java.util.Optional;

public class StubConcertReservationReaderRepository implements ConcertReservationReaderRepository {
    @Override
    public Optional<Reservation> findNotAssignedBySeatId(Long seatId, AssignmentStatus status) {
        return Optional.of(
                Reservation.builder()
                        .reservationId(1L)
                        .status(AssignmentStatus.NOT_BE_ASSIGNED)
                        .version(0)
                        .build()
        );
    }
}
