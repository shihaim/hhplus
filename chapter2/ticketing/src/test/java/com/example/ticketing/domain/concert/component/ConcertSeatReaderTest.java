package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.repository.ConcertSeatReaderRepository;
import com.example.ticketing.domain.concert.repository.StubConcertSeatReaderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class ConcertSeatReaderTest {

    private final ConcertSeatReaderRepository stubReaderRepository = new StubConcertSeatReaderRepository();
    private final ConcertSeatReader sut = new ConcertSeatReader(stubReaderRepository);

    /**
     * [해당 날짜의 좌석 조회]
     * case1: 해당 날짜의 좌석 조회 성공
     */

    @Test
    @DisplayName("해당 날짜의 좌석 조회 성공")
    void case1() throws Exception {
        //given
        String concertCode = "IU_BLUEMING_001";
        LocalDateTime concertDate = LocalDateTime.of(2024, 4, 30, 15, 0, 0);

        //when
        List<Seat> findSeats = sut.findAvailableSeats(concertCode, concertDate);

        //then
        Assertions.assertThat(findSeats.size()).isEqualTo(10);
    }
}