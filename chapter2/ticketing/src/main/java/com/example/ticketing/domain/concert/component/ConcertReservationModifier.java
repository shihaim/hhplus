package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.AssignmentStatus;
import com.example.ticketing.domain.concert.entity.Reservation;
import com.example.ticketing.domain.concert.repository.ConcertReservationReaderRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ConcertReservationModifier {

    private final ConcertReservationReaderRepository readerRepository;

    public ConcertReservationModifier(ConcertReservationReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    /**
     * 좌석 예매 요청 - 좌석 임시 배정
     */
    public Reservation reserveSeat(Long seatId, String userUUID, int token) {
        Reservation findReservation = readerRepository.findNotAssignedBySeatId(seatId, AssignmentStatus.NOT_BE_ASSIGNED)
                .orElseThrow(() -> new NoSuchElementException("배정할 수 없는 좌석입니다."));

        try {
            findReservation.assignment(userUUID, token);
            return findReservation;
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("이미 임시 배정된 좌석입니다.");
        }
    }
}
