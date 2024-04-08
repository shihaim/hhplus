package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenStoreRepository;
import com.example.ticketing.domain.token.repository.StubQueueTokenStoreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QueueTokenStoreTest {

    private final QueueTokenStoreRepository stubStoreRepository = new StubQueueTokenStoreRepository();
    private final QueueTokenStore sut = new QueueTokenStoreImpl(stubStoreRepository);

    /**
     * [대기열 토큰 생성]
     * case1: 대기열 토큰 생성 성공
     */
    @Test
    @DisplayName("대기열 토큰 생성 성공")
    void case1() throws Exception {
        //given
        QueueToken createQueueToken = QueueToken.builder()
                .queueTokenId(1L)
                .build();

        //when
        Long savedQueueTokenId = sut.createQueueToken(createQueueToken);

        //then
        Assertions.assertThat(savedQueueTokenId).isEqualTo(createQueueToken.getQueueTokenId());
    }
}