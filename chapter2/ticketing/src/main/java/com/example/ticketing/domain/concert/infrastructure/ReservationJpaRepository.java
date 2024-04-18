package com.example.ticketing.domain.concert.infrastructure;

import com.example.ticketing.domain.concert.entity.AssignmentStatus;
import com.example.ticketing.domain.concert.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findBySeatSeatIdAndStatus(Long seatId, AssignmentStatus status);

    Optional<Reservation> findByUserUUIDAndToken(String userUUID, int token);
}
