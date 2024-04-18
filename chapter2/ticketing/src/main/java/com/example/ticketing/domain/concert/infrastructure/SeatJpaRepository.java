package com.example.ticketing.domain.concert.infrastructure;

import com.example.ticketing.domain.concert.entity.ConcertPK;
import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByConcert_ConcertPK(ConcertPK concertPK);

    Optional<Seat> findByConcert_ConcertPKAndSeatNumberAndStatus(ConcertPK concertPK, int seatNumber, TicketingStatus status);
}
