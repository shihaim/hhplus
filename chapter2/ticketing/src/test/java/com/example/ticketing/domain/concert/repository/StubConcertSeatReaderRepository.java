package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class StubConcertSeatReaderRepository implements ConcertSeatReaderRepository {
    @Override
    public List<Seat> findAllByCodeAndDate(String concertCode, LocalDateTime concertDate) {
        return List.of(
                Seat.builder().build(),
                Seat.builder().build(),
                Seat.builder().build(),
                Seat.builder().build(),
                Seat.builder().build(),
                Seat.builder().build(),
                Seat.builder().build(),
                Seat.builder().build(),
                Seat.builder().build(),
                Seat.builder().build()
        );
    }

    @Override
    public Optional<Seat> findByCodeAndDateAndStatus(String concertCode, LocalDateTime concertDate, int seatNumber, TicketingStatus status) {
        if (status == TicketingStatus.COMPLETE) return Optional.empty(); // fake??

        return Optional.of(
                Seat.builder()
                        .seatId(1L)
                        .concert(Concert.builder().build())
                        .seatNumber(30)
                        .status(TicketingStatus.NONE)
                        .build()
        );
    }
}
