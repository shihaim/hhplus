package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class QueueTokenReaderV2 {

    @Qualifier("queueTokenCoreReaderRepository")
    private final QueueTokenReaderRepository readerRepository;

    /**
     * 대기열 토큰 조회
     */
    public QueueToken findQueueToken(String userUUID, int token) {
        return readerRepository.findByUserUUIDAndToken(userUUID, token)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 토큰입니다."));
    }

    /**
     * TODO [단위 테스트 작성]
     * 대기열을 진입한 마지막 토큰 순번 조회
     */
    public long findLastEnteredToken(String concertCode, long tokenId, QueueStatus status) {
        switch (status) {
            case WAITING -> {
                long lastQueueNumber = readerRepository.findLastQueueNumber(concertCode, QueueStatus.IN_PROGRESS, QueueStatus.EXPIRED)
                        .orElse(0L);
                return tokenId - lastQueueNumber;
            }
            // EXPIRED인 경우는 Interceptor에서 터지므로 없는 경우라고 봐도 무방
            default -> {
                return  0L;
            }
        }
    }
}
