package com.example.ticketing.domain.token.repository;

import com.example.ticketing.domain.token.entity.QueueTokenInfo;

import java.util.List;
import java.util.Set;

public interface QueueTokenReaderRepositoryV2 {

    /**
     * 내 Rank 조회
     */
    QueueTokenInfo findMyRank(QueueTokenInfo queueTokenInfo);

    /**
     * In-Progress Count 조회
     */
    Set<String> findInProgressTokenCount();

    /**
     * Waiting Token Pop(Select)
     */
    List<QueueTokenInfo> popWaitingTokens(long count);
}
