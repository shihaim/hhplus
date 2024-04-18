package com.example.ticketing.domain.concert.infrastructure;

import com.example.ticketing.domain.concert.entity.AssignmentStatus;
import com.example.ticketing.domain.concert.entity.Reservation;
import com.example.ticketing.domain.concert.repository.ReservationReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationCoreReaderRepository implements ReservationReaderRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Optional<Reservation> findNotAssignedBySeatId(Long seatId, AssignmentStatus status) {
        return reservationJpaRepository.findBySeatSeatIdAndStatus(seatId, status);
    }

    @Override
    public Optional<Reservation> findAssignedByUserUUID(String userUUID, int token) {
        return reservationJpaRepository.findByUserUUIDAndToken(userUUID, token);
    }
}
