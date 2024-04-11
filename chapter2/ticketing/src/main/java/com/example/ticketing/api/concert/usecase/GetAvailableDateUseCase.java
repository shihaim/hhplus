package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.AvailableConcertDateResponse;
import com.example.ticketing.domain.concert.component.ConcertReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAvailableDateUseCase {

    private final ConcertReader concertReader;

    /**
     * 예매 가능 날짜 조회
     */
    public List<AvailableConcertDateResponse> execute(String concertCode) {
        return concertReader.findConcertDates(concertCode).stream()
                .map(AvailableConcertDateResponse::convert)
                .toList();
    }
}
