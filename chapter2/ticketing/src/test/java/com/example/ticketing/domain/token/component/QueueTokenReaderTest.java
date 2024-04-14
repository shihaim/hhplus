package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenReaderRepository;
import com.example.ticketing.domain.token.repository.StubQueueTokenReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class QueueTokenReaderTest {

    private final QueueTokenReaderRepository stubReaderRepository = new StubQueueTokenReaderRepository();
    private final QueueTokenReader sut = new QueueTokenReader(stubReaderRepository);

    /**
     * [대기열 토큰 조회]
     * case1: 대기열 토큰 조회 성공
     */

    @Test
    @DisplayName("대기열 토큰 조회 성공")
    void case1() throws Exception {
        //given
        String concertCode = "IU_BLUEMING_001";
        String userUUID = "1e9ebe68-045a-49f1-876e-a6ea6380dd5c";
        LocalDateTime issuedAt = LocalDateTime.of(2024, 4, 11, 13, 20, 35);

        int token = userUUID.hashCode();
        token = 31 * token + issuedAt.hashCode();
        token = 31 * token + concertCode.hashCode();

        //when
        QueueToken findQueueToken = sut.findQueueToken(userUUID);

        //then
        assertThat(findQueueToken.getQueueTokenId()).isEqualTo(1L);
        assertThat(findQueueToken.getUser().getUserUUID()).isEqualTo(userUUID);
        assertThat(findQueueToken.getConcertCode()).isEqualTo("IU_BLUEMING_001");
        assertThat(findQueueToken.getToken()).isEqualTo(token);
        assertThat(findQueueToken.getStatus()).isEqualTo(QueueStatus.IN_PROGRESS);
        assertThat(findQueueToken.getIssuedAt()).isEqualTo(issuedAt);
    }
}