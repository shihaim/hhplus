package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.AssignmentStatus;
import com.example.ticketing.domain.concert.entity.Reservation;
import com.example.ticketing.domain.concert.repository.ConcertReservationReaderRepository;
import com.example.ticketing.domain.concert.repository.StubConcertReservationReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ConcertReservationModifierTest {

    private final ConcertReservationReaderRepository stubReaderRepository = new StubConcertReservationReaderRepository();
    private final ConcertReservationModifier sut = new ConcertReservationModifierImpl(stubReaderRepository);

    /**
     * [좌석 예매 요청]
     * case1: 좌석 예매 요청
     */

    @Test
    @DisplayName("좌석 예매 요청")
    void case1() throws Exception {
        //given
        long seatId = 1L;

        String userUUID = "1e9ebe68-045a-49f1-876e-a6ea6380dd5c";
        String concertCode = "IU_BLUEMING_001";
        LocalDateTime expiredAt = LocalDateTime.of(2024, 4, 11, 13, 20, 35).plusMinutes(10);

        int token = userUUID.hashCode();
        token = 31 * token + expiredAt.hashCode();
        token = 31 * token + concertCode.hashCode();

        //when
        Reservation result = sut.reserveSeat(seatId, userUUID, token);

        //then
        assertThat(result.getReservationId()).isEqualTo(1L);
        assertThat(result.getToken()).isEqualTo(token);
        assertThat(result.getUserUUID()).isEqualTo(userUUID);
        assertThat(result.getStatus()).isEqualTo(AssignmentStatus.ASSIGNED);
        assertThat(result.getAssignedAt().withNano(0)).isEqualTo(LocalDateTime.now().plusMinutes(5).withNano(0));
        assertThat(result.getVersion()).isEqualTo(1);
    }
}