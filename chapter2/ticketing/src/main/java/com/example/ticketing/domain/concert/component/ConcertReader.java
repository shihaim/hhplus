package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Concert;

import java.util.List;

public interface ConcertReader {

    /**
     * 콘서트 예매 가능한 날짜 조회
     */
    List<Concert> findConcertDates(String concertCode);
}
