package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.IssuedTokenResponse;
import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.infrastructure.ConcertJpaRepository;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.infrastructure.QueueTokenJpaRepository;
import com.example.ticketing.domain.user.entity.User;
import com.example.ticketing.domain.user.infrastructure.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * IssueQueueTokenUseCase 통합 테스트
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class IssueQueueTokenUseCaseTest {

    @Autowired
    private IssueQueueTokenUseCase issueQueueTokenUseCase;

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private QueueTokenJpaRepository queueTokenJpaRepository;

    @Test
    @DisplayName("토큰 발급 통합 테스트")
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

        //when
        IssuedTokenResponse result = issueQueueTokenUseCase.execute(concertCode, userUUID);
        QueueToken findQueueToken = queueTokenJpaRepository.findById(result.tokenId()).get();

        //then
        assertThat(result.tokenId()).isEqualTo(findQueueToken.getQueueTokenId());
        assertThat(result.token()).isEqualTo(findQueueToken.getToken());
    }
}