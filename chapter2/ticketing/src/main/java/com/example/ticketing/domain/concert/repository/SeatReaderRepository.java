package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatReaderRepository {

    List<Seat> findAllByCodeAndDate(String concertCode, LocalDateTime concertDate);

    Optional<Seat> findByCodeAndDateAndStatus(String concertCode, LocalDateTime concertDate, int seatNumber, TicketingStatus status);
}
