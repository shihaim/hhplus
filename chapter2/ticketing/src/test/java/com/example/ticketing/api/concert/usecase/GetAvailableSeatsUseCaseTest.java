package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.AvailableConcertSeatResponse;
import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;
import com.example.ticketing.domain.concert.infrastructure.ConcertJpaRepository;
import com.example.ticketing.domain.concert.infrastructure.SeatJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GetAvailableSeatsUseCase 통합 테스트
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GetAvailableSeatsUseCaseTest {

    @Autowired
    private GetAvailableSeatsUseCase getAvailableSeatsUseCase;

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private SeatJpaRepository seatJpaRepository;

    @Test
    @DisplayName("해당 날짜의 좌석 조회 통합 테스트")
    void case1() throws Exception {
        //given
        String concertCode = "IU_BLUEMING_001";

        LocalDateTime concertDate = LocalDateTime.of(2024, 5, 10, 15, 30, 0);
        Concert createConcert = Concert.builder()
                .concertCode(concertCode)
                .concertDate(concertDate)
                .concertName("아이유 블루밍 콘서트")
                .price(79900)
                .build();
        Concert savedConcert = concertJpaRepository.save(createConcert);

        for (int i = 1; i <= 10; i++) {
            Seat createSeat = Seat.builder()
                    .concert(savedConcert)
                    .seatNumber(i)
                    .status(TicketingStatus.NONE)
                    .build();

            seatJpaRepository.save(createSeat);
        }

        //when
        List<AvailableConcertSeatResponse> result = getAvailableSeatsUseCase.execute(concertCode, concertDate);

        //then
        assertThat(result.size()).isEqualTo(10);
    }
}