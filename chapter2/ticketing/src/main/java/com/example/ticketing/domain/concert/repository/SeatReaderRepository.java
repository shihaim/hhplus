package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.ConcertPK;
import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;

import java.util.List;
import java.util.Optional;

public interface SeatReaderRepository {
    List<Seat> findAllByConcertPk(ConcertPK concertPK);

    Optional<Seat> findByConcertPKAndSeatNumberAndStatus(ConcertPK concertPK, int seatNumber, TicketingStatus status);
}
