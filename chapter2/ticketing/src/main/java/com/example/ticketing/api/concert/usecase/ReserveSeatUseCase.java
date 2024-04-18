package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.ReservationResponse;
import com.example.ticketing.domain.concert.component.ConcertReader;
import com.example.ticketing.domain.concert.component.ConcertReservationValidator;
import com.example.ticketing.domain.concert.component.ConcertSeatReader;
import com.example.ticketing.domain.concert.entity.Reservation;
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
    private final ConcertReservationValidator concertReservationValidator;

    /**
     * 좌석 임시 배정
     */
    public ReservationResponse execute(String userUUID, int token, String concertCode, LocalDateTime concertDate, int seatNumber) {
        // 1. 콘서트 존재 여부 체크
        concertReader.findByConcertCodeAndDate(concertCode, concertDate);
        // 2. 좌석 존재 여부 및 예매 가능한 좌석인지 체크
        Seat findNotCompletedSeat = concertSeatReader.findNotCompletedSeat(concertCode, concertDate, seatNumber);
        // 3. 예약이 가능한 좌석인지 체크
        // Getter vs ConcertReservationReader를 통해 조회 (둘 다 DB를 탐.) -> Getter는 객체 null 여부를 확인해야할 듯?
        Reservation findReservation = findNotCompletedSeat.getReservation();
        concertReservationValidator.isAvailableReservation(findReservation.getAssignedAt());
        // 4. 좌석 임시 배정
        LocalDateTime assignedAt = findReservation.assignment(userUUID, token); // Transaction 종료시 OptimisticLockException 발생 -> try-catch 필요
        // throw new OptimisticLockException("이미 임시 배정된 좌석입니다.");

        return ReservationResponse.convert(concertCode, concertDate, seatNumber, assignedAt);
    }
}
