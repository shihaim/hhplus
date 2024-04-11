package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.AssignmentStatus;
import com.example.ticketing.domain.concert.entity.Reservation;

import java.time.LocalDateTime;
import java.util.Optional;

public class StubReservationReaderRepository implements ReservationReaderRepository {
    @Override
    public Optional<Reservation> findNotAssignedBySeatId(Long seatId, AssignmentStatus status) {
        return Optional.of(
                Reservation.builder()
                        .reservationId(1L)
                        .status(AssignmentStatus.NOT_BE_ASSIGNED)
                        .assignedAt(LocalDateTime.now().minusSeconds(1))
                        .build()
        );
    }
}
