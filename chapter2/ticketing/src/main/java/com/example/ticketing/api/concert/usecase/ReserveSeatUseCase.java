package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.ReservationResponse;
import com.example.ticketing.domain.concert.component.ConcertReader;
import com.example.ticketing.domain.concert.component.ConcertSeatReader;
import com.example.ticketing.domain.concert.entity.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ReserveSeatUseCase {

    private final ConcertReader concertReader;
    private final ConcertSeatReader concertSeatReader;

    /**
     * 좌석 임시 배정
     */
    public ReservationResponse execute(String userUUID, int token, String concertCode, LocalDateTime concertDate, int seatNumber) {
        // 1. 콘서트 존재 여부 체크
        concertReader.findByConcertCodeAndDate(concertCode, concertDate);
        // 2. 좌석 존재 여부 및 예매 가능한 좌석인지 체크
        Seat findNotCompletedSeat = concertSeatReader.findNotCompletedSeat(concertCode, concertDate, seatNumber);
        // 3. 좌석 임시 배정
        LocalDateTime assignedAt = findNotCompletedSeat.getReservation().assignment(userUUID, token);

        return ReservationResponse.convert(concertCode, concertDate, seatNumber, assignedAt);
    }
}
