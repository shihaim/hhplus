package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueToken;

public interface QueueTokenStore {

    /**
     * 대기열 토큰 생성
     */
    Long createQueueToken(QueueToken createQueueToken);
}
