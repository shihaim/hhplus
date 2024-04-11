package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.repository.ReservationReaderRepository;
import com.example.ticketing.domain.concert.repository.StubReservationReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConcertReservationValidatorTest {

    private final ConcertReservationValidator sut = new ConcertReservationValidator();
    private final ReservationReaderRepository stubReaderRepository = new StubReservationReaderRepository();

    /**
     * [콘서트 좌석 예약 Validator]
     * case1: 임시 배정된 시간이 만료된 경우 Error Throw
     */

    @Test
    @DisplayName("임시 배정된 시간이 만료된 경우 Error Throw")
    void case1() throws Exception {
        //given
        LocalDateTime assignedAt = stubReaderRepository.findNotAssignedBySeatId(Mockito.any(), Mockito.any()).get().getAssignedAt();

        //when
        RuntimeException e = assertThrows(
                RuntimeException.class,
                () -> sut.isExpired(assignedAt)
        );

        //then
        assertThat(e.getMessage()).isEqualTo("임시 배정된 시간이 만료되었습니다.");
    }
}