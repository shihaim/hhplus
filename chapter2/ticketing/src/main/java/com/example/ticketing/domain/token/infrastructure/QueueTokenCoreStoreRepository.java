package com.example.ticketing.domain.token.infrastructure;

import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueueTokenCoreStoreRepository implements QueueTokenStoreRepository {

    private final QueueTokenJpaRepository queueTokenJpaRepository;

    @Override
    public QueueToken save(QueueToken createQueueToken) {
        return queueTokenJpaRepository.save(createQueueToken);
    }
}
