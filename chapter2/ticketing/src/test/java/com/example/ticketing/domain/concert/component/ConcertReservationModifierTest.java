package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.AssignmentStatus;
import com.example.ticketing.domain.concert.entity.Reservation;
import com.example.ticketing.domain.concert.repository.ReservationReaderRepository;
import com.example.ticketing.domain.concert.repository.StubReservationReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ConcertReservationModifierTest {

    private final ReservationReaderRepository stubReaderRepository = new StubReservationReaderRepository();
    private final ConcertReservationModifier sut = new ConcertReservationModifier(stubReaderRepository);

    /**
     * [좌석 임시 배정]
     * case1: 좌석 임시 배정
     */

    @Test
    @DisplayName("좌석 임시 배정")
    void case1() throws Exception {
        //given
        long seatId = 1L;

        String userUUID = "1e9ebe68-045a-49f1-876e-a6ea6380dd5c";
        String concertCode = "IU_BLUEMING_001";
        LocalDateTime issuedAt = LocalDateTime.of(2024, 4, 11, 13, 20, 35);

        int token = userUUID.hashCode();
        token = 31 * token + issuedAt.hashCode();
        token = 31 * token + concertCode.hashCode();

        //when
        Reservation result = sut.reserveSeat(seatId, userUUID, token);

        //then
        assertThat(result.getReservationId()).isEqualTo(1L);
        assertThat(result.getToken()).isEqualTo(token);
        assertThat(result.getUserUUID()).isEqualTo(userUUID);
        assertThat(result.getStatus()).isEqualTo(AssignmentStatus.ASSIGNED);
        assertThat(result.getAssignedAt().withNano(0)).isEqualTo(LocalDateTime.now().plusMinutes(2).withNano(0));
    }
}