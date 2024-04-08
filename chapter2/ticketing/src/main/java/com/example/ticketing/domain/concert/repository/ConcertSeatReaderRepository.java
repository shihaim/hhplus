package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.Seat;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertSeatReaderRepository {

    List<Seat> findAllByCodeAndDate(String concertCode, LocalDateTime concertDate);
}
