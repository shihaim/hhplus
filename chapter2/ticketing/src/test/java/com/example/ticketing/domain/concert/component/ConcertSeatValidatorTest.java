package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.repository.SeatReaderRepository;
import com.example.ticketing.domain.concert.repository.StubSeatReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConcertSeatValidatorTest {

    private final ConcertSeatValidator sut = new ConcertSeatValidator();
    private final SeatReaderRepository stubReaderRepository = new StubSeatReaderRepository();

    /**
     * [콘서트 좌석 Validator]
     * case1: 존재하지 않는 좌석인 경우 Error Throw
     */

    @Test
    @DisplayName("존재하지 않는 좌석인 경우 Error Throw")
    void case1() throws Exception {
        //given
        int seatNumber = -1;
        List<Seat> findSeats = stubReaderRepository.findAllByConcertPk(Mockito.any());

        //when
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> sut.isExists(findSeats, seatNumber)
        );

        //then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 좌석입니다");
    }
}