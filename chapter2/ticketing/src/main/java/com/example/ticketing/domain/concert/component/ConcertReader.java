package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.repository.ConcertReaderRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConcertReader {

    private final ConcertReaderRepository readerRepository;

    public ConcertReader(ConcertReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    /**
     * 콘서트 예매 가능한 날짜 조회
     */
    public List<Concert> findConcertDates(String concertCode) {
        return readerRepository.findAllByConcertCode(concertCode);
    }
}
