package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Seat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConcertSeatReader {

    /**
     * 해당 날짜의 예매 가능한 좌석 조회
     */
    List<Seat> findAvailableSeats(String concertCode, LocalDateTime concertDate);

    /**
     * 예매가 되지 않은 좌석 조회
     */
    Optional<Seat> findNotCompletedSeat(String concertCode, LocalDateTime concertDate, int seatNumber);
}
