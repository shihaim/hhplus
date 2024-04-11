package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class StubSeatReaderRepository implements SeatReaderRepository {
    @Override
    public List<Seat> findAllByCodeAndDate(String concertCode, LocalDateTime concertDate) {
        return List.of(
                Seat.builder().seatNumber(1).build(),
                Seat.builder().seatNumber(2).build(),
                Seat.builder().seatNumber(3).build(),
                Seat.builder().seatNumber(4).build(),
                Seat.builder().seatNumber(5).build(),
                Seat.builder().seatNumber(6).build(),
                Seat.builder().seatNumber(7).build(),
                Seat.builder().seatNumber(8).build(),
                Seat.builder().seatNumber(9).build(),
                Seat.builder().seatNumber(10).build()
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
