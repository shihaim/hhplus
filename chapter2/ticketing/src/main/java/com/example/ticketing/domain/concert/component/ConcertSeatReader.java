package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Seat;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertSeatReader {

    /**
     * 해당 날짜의 예매 가능한 좌석 조회
     */
    List<Seat> findConcertSeats(String concertCode, LocalDateTime concertDate);
}
