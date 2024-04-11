package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenStoreRepository;
import com.example.ticketing.domain.token.repository.StubQueueTokenStoreRepository;
import com.example.ticketing.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class QueueTokenStoreTest {

    private final QueueTokenStoreRepository stubStoreRepository = new StubQueueTokenStoreRepository();
    private final QueueTokenStore sut = new QueueTokenStore(stubStoreRepository);

    /**
     * [대기열 토큰 생성]
     * case1: 대기열 토큰 생성 성공
     */
    @Test
    @DisplayName("대기열 토큰 생성 성공")
    void case1() throws Exception {
        //given
        String concertCode = "IU_BLUEMING_001";
        String userUUID = "1e9ebe68-045a-49f1-876e-a6ea6380dd5c";
        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(10);

        int token = userUUID.hashCode();
        token = 31 * token + expiredAt.hashCode();
        token = 31 * token + concertCode.hashCode();

        QueueToken createQueueToken = QueueToken.builder()
                .queueTokenId(1L)
                .user(User.builder().userUUID(userUUID).build())
                .concertCode(concertCode)
                .token(token)
                .status(QueueStatus.WAITING)
                .expiredAt(expiredAt)
                .build();

        //when
        QueueToken result = sut.saveQueueToken(createQueueToken);

        //then
        assertThat(result.getQueueTokenId()).isEqualTo(1L);
        assertThat(result.getUser().getUserUUID()).isEqualTo(userUUID);
        assertThat(result.getConcertCode()).isEqualTo(concertCode);
        assertThat(result.getToken()).isEqualTo(token);
        assertThat(result.getStatus()).isEqualTo(QueueStatus.WAITING);
        assertThat(result.getExpiredAt()).isEqualTo(expiredAt);
    }
}