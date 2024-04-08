package com.example.ticketing.domain.token.repository;

import com.example.ticketing.domain.token.entity.QueueToken;

public class StubQueueTokenStoreRepository implements QueueTokenStoreRepository {
    @Override
    public Long save(QueueToken createQueueToken) {
        return 1L;
    }
}
