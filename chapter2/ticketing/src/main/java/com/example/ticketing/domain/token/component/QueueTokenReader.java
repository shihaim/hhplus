package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueToken;

public interface QueueTokenReader {

    /**
     * 대기열 토큰 조회
     */
    QueueToken findQueueToken(String userUUID);
}
