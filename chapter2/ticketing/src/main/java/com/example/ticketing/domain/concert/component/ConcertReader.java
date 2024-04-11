package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.repository.ConcertReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertReader {

    private final ConcertReaderRepository readerRepository;

    /**
     * 콘서트 예매 가능한 날짜 조회
     */
    @Transactional(readOnly = true)
    public List<Concert> findConcertDates(String concertCode) {
        return readerRepository.findAllByConcertCode(concertCode);
    }
}
