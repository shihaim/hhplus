package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.repository.ConcertReaderRepository;
import com.example.ticketing.domain.concert.repository.StubConcertReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConcertReaderTest {

    private final ConcertReaderRepository stubReaderRepository = new StubConcertReaderRepository();
    private final ConcertReader sut = new ConcertReaderImpl(stubReaderRepository);

    /**
     * [콘서트 예매 가능한 날짜 조회]
     * case1: 콘서트 예매 가능한 날짜 조회 성공
     */

    @Test
    @DisplayName("콘서트 예매 가능한 날짜 조회 성공")
    void case1() throws Exception {
        //given
        String concertCode = "IU_BLUEMING_001";

        //when
        List<Concert> findConcertDates = sut.findConcertDates(concertCode);

        //then
        assertThat(findConcertDates.size()).isEqualTo(3);
    }
}