package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.entity.ConcertPK;
import com.example.ticketing.domain.concert.repository.ConcertReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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

    /**
     * TODO [단위 테스트]
     * 콘서트 예매 가능한 날짜 조회
     */
    public Concert findConcert(String concertCode, LocalDateTime concertDate) {
        return readerRepository.findByConcertPK(ConcertPK.of(concertCode, concertDate))
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 콘서트입니다."));
    }
}
