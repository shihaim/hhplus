package com.example.ticketing.domain.token.repository;

import com.example.ticketing.domain.token.entity.QueueTokenInfo;

public interface QueueTokenStoreRepositoryV2 {

    /**
     * 대기열 토큰 저장
     */
    void save(QueueTokenInfo createQueueTokenInfo);

    /**
     * 대기열 진입
     */
    void inProgress(QueueTokenInfo waitingToken);
}
