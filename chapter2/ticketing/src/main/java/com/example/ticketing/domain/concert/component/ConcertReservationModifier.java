package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Reservation;

import java.time.LocalDateTime;

public interface ConcertReservationModifier {

    /**
     * 좌석 예매 요청 - 좌석 임시 배정
     */
    Reservation reserveSeat(Long seatId, String userUUID, int token);
}