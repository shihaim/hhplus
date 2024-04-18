package com.example.ticketing.domain.token.infrastructure;

import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueueTokenCoreReaderRepository implements QueueTokenReaderRepository {

    private final QueueTokenJpaRepository queueTokenJpaRepository;

    @Override
    public Optional<QueueToken> findByUserUUIDAndToken(String userUUID, int token) {
        return Optional.empty();
    }

    @Override
    public long findLastQueueNumber(QueueStatus inProgress, QueueStatus expired) {
        return 0;
    }
}
