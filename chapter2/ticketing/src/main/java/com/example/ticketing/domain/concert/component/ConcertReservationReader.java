package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Reservation;
import com.example.ticketing.domain.concert.repository.ReservationReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ConcertReservationReader {

    private final ReservationReaderRepository readerRepository;

    /**
     * TODO [단위 테스트]
     * 좌석 예매 요청 - 임시 배정된 좌석 조회
     */
    public Reservation findAssignedSeat(String userUUID, int token) {
        return readerRepository.findAssignedByUserUUID(userUUID, token)
                .orElseThrow(() -> new NoSuchElementException("임시 배정된 좌석이 존재하지 않습니다."));
    }

}
