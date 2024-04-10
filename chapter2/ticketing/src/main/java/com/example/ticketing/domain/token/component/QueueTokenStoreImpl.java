package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenStoreRepository;

public class QueueTokenStoreImpl implements QueueTokenStore {

    private final QueueTokenStoreRepository storeRepository;

    public QueueTokenStoreImpl(QueueTokenStoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public QueueToken saveQueueToken(QueueToken createQueueToken) {
        return storeRepository.save(createQueueToken);
    }
}
