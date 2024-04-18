package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.QueuePollingResponse;
import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.infrastructure.ConcertJpaRepository;
import com.example.ticketing.domain.token.component.QueueTokenStore;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.user.entity.User;
import com.example.ticketing.domain.user.infrastructure.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * QueuePollingUseCase 통합 테스트
 */
@SpringBootTest
class QueuePollingUseCaseTest {

    @Autowired
    private QueuePollingUseCase queuePollingUseCase;

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private QueueTokenStore queueTokenStore;

    @Test
    @DisplayName("대기열 순빈 폴링 통합 테스트")
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

        String userUUID = UUID.randomUUID().toString();
        User createUser = User.builder()
                .userUUID(userUUID)
                .balance(30000)
                .build();
        User savedUser = userJpaRepository.save(createUser);

        QueueToken createQueueToken = QueueToken.createQueueToken(concertCode, createUser);
        QueueToken savedQueueToken = queueTokenStore.saveQueueToken(createQueueToken);

        //when
        QueuePollingResponse result = queuePollingUseCase.execute(concertCode, userUUID, savedQueueToken.getToken());

        //then
        assertThat(result.tokenId()).isEqualTo(savedQueueToken.getQueueTokenId());
        assertThat(result.token()).isEqualTo(savedQueueToken.getToken());
        assertThat(result.status()).isEqualTo(savedQueueToken.getStatus());
    }
}