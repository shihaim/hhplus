package com.example.ticketing.domain.token.repository;

import com.example.ticketing.domain.token.entity.QueueToken;

public interface QueueTokenStoreRepository {

    QueueToken save(QueueToken createQueueToken);

    void delete(QueueToken target);
}
