package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueTokenInfo;
import com.example.ticketing.domain.token.repository.QueueTokenStoreRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class QueueTokenStoreV2 {

    @Qualifier("queueTokenRedisStoreRepository")
    private final QueueTokenStoreRepositoryV2 storeRepository;

    /**
     * 대기열 토큰 생성
     */
    @Transactional
    public void saveQueueToken(QueueTokenInfo createQueueTokenInfo) {
        storeRepository.save(createQueueTokenInfo);
    }

    /**
     * 대기열 진입
     */
    public boolean inProgress(List<QueueTokenInfo> waitingTokens, String userUUID, int token) {
        AtomicBoolean isInProgress = new AtomicBoolean(false);
        waitingTokens.forEach(waitingToken -> {
            if (waitingToken.getUserUUID().equals(userUUID) && waitingToken.getToken() == token) {
                isInProgress.set(true);
            }
            storeRepository.inProgress(waitingToken);
        });

        return isInProgress.get();
    }
}
