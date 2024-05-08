package com.example.ticketing.domain.token.infrastructure;

import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("queueTokenRedisReaderRepository")
@RequiredArgsConstructor
public class QueueTokenRedisReaderRepository implements QueueTokenReaderRepository {

    private final RedisTemplate<String, Integer> redisTemplate;

    @Override
    public Optional<QueueToken> findByUserUUIDAndToken(String userUUID, int token) {
        return Optional.empty();
    }

    @Override
    public Optional<Long> findLastQueueNumber(String concertCode, QueueStatus inProgress, QueueStatus expired) {
        return Optional.empty();
    }
}
