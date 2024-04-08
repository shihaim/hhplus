package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenReaderRepository;
import com.example.ticketing.domain.token.repository.StubQueueTokenReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueueTokenReaderTest {

    private final QueueTokenReaderRepository stubReaderRepository = new StubQueueTokenReaderRepository();
    private final QueueTokenReader sut = new QueueTokenReaderImpl(stubReaderRepository);

    /**
     * [대기열 토큰 조회]
     * case1: 대기열 토큰 조회 실패 - 존재하지 않는 토큰
     * case2: 대기열 토큰 조회 성공
     */

    @Test
    @DisplayName("대기열 토큰 조회 실패 - 존재하지 않는 토큰")
    void case1() throws Exception {
        //given
        String userUUID = UUID.randomUUID().toString();

        //when
        NoSuchElementException e = assertThrows(
                NoSuchElementException.class,
                () -> sut.findQueueToken(userUUID)
        );

        //then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 토큰입니다.");
    }

    @Test
    @DisplayName("대기열 토큰 조회 성공")
    void case2() throws Exception {
        //given
        String userUUID = "1e9ebe68-045a-49f1-876e-a6ea6380dd5c";
        LocalDateTime expiredAt = LocalDateTime.of(2024, 4, 11, 13, 20, 35).plusMinutes(10);

        //when
        QueueToken findQueueToken = sut.findQueueToken(userUUID);

        //then
        assertThat(findQueueToken.getQueueTokenId()).isEqualTo(1L);
        assertThat(findQueueToken.getUser().getUserUUID()).isEqualTo(userUUID);
        assertThat(findQueueToken.getConcertCode()).isEqualTo("IU_BLUEMING_001");
        assertThat(findQueueToken.getStatus()).isEqualTo(QueueStatus.IN_PROGRESS);
        assertThat(findQueueToken.getExpiredAt()).isEqualTo(expiredAt);
    }
}