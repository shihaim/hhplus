package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueTokenStore {

    private final QueueTokenStoreRepository storeRepository;

    /**
     * 대기열 토큰 생성
     */
    public QueueToken saveQueueToken(QueueToken createQueueToken) {
        return storeRepository.save(createQueueToken);
    }
}
