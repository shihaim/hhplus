package com.example.ticketing.domain.token.repository;

import com.example.ticketing.domain.token.entity.QueueToken;

public interface QueueTokenStoreRepositoryV2 {

    QueueToken save(QueueToken createQueueToken);
}
