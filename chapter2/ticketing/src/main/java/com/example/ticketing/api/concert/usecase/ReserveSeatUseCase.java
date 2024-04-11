package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.ReservationResponse;
import com.example.ticketing.domain.concert.component.ConcertReader;
import com.example.ticketing.domain.concert.component.ConcertSeatReader;
import com.example.ticketing.domain.concert.component.ConcertSeatValidator;
import com.example.ticketing.domain.concert.component.ConcertValidator;
import com.example.ticketing.domain.user.component.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReserveSeatUseCase {

    private final ConcertReader concertReader;
    private final ConcertValidator concertValidator;
    private final ConcertSeatReader concertSeatReader;
    private final ConcertSeatValidator concertSeatValidator;
    private final UserReader userReader;

    /**
     * 좌석 임시 배정
     */
    public ReservationResponse execute() {
        // 1. concertCode, concertDate로 Optional<Concert> 조회후 empty 체크
        // 2. findNotCompletedSeat(예매가 되지 않는 좌석 조회)
        // 3. 좌석 존재여부 체크

        return null;
    }
}
