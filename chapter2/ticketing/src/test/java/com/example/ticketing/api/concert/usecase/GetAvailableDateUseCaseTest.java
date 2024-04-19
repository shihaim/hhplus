package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.AvailableConcertDateResponse;
import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.infrastructure.ConcertJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GetAvailableDateUseCase 통합 테스트
 */
@SpringBootTest
class GetAvailableDateUseCaseTest {

    @Autowired
    private GetAvailableDateUseCase getAvailableDateUseCase;

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Test
    @DisplayName("예매 가능 날짜 조회 통합 테스트")
    void case1() throws Exception {
        //given
        String concertCode = "IU_BLUEMING_001";
        Concert createConcert = Concert.builder()
                .concertCode(concertCode)
                .concertDate(LocalDateTime.of(2024, 5, 10, 15, 30, 0))
                .concertName("아이유 블루밍 콘서트")
                .price(79900)
                .build();
        Concert savedConcert = concertJpaRepository.save(createConcert);

        //when
        List<AvailableConcertDateResponse> result = getAvailableDateUseCase.execute(concertCode);

        //then
        assertThat(result.size()).isEqualTo(1);
    }
}