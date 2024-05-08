package com.example.ticketing.index;

import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;
import com.example.ticketing.domain.concert.infrastructure.SeatJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class IndexTest {

    @Autowired
    private SeatJpaRepository seatJpaRepository;

    @Test
    @DisplayName("Index test1")
    void case1_1() throws Exception {
        String concertCode = "IU_0011";
        LocalDateTime concertDate = LocalDateTime.of(2024, 5, 18, 15, 30, 0);
        List<Seat> list = seatJpaRepository.findSeatList(concertCode, concertDate, TicketingStatus.NONE);
        Assertions.assertThat(list.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Index test1")
    void case1_2() throws Exception {
        String concertCode = "IU_0011";
        LocalDateTime concertDate = LocalDateTime.of(2024, 5, 18, 15, 30, 0);
        List<Seat> list = seatJpaRepository.findSeatList(concertCode, concertDate, TicketingStatus.NONE, TicketingStatus.COMPLETE);
        Assertions.assertThat(list.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Index test2")
    void case2() throws Exception {
        String concertCode = "IU_0011";
        LocalDateTime concertDate = LocalDateTime.of(2024, 5, 18, 15, 30, 0);
        List<Seat> list = seatJpaRepository.findSeatList(concertCode, concertDate);
        Assertions.assertThat(list.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Index test3")
    void case3() throws Exception {
        List<Seat> list = seatJpaRepository.findSeatList();
        Assertions.assertThat(list.size()).isGreaterThan(0);
    }
}
