package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.Seat;

import java.time.LocalDateTime;
import java.util.List;

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
}
