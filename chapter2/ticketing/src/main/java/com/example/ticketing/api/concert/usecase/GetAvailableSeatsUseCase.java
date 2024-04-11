package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.AvailableConcertSeatResponse;
import com.example.ticketing.domain.concert.component.ConcertSeatReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAvailableSeatsUseCase {

    private final ConcertSeatReader concertSeatReader;

    /**
     * 해당 날짜의 좌석 조회
     */
    public List<AvailableConcertSeatResponse> execute(String concertCode, LocalDateTime concertDate) {
        return concertSeatReader.findAvailableSeats(concertCode, concertDate.withNano(0)).stream()
                .map(AvailableConcertSeatResponse::convert)
                .toList();
    }
}
