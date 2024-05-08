package com.example.ticketing.domain.token.infrastructure;

import com.example.ticketing.config.RedisKey;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenStoreRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository("queueTokenRedisStoreRepository")
@RequiredArgsConstructor
public class QueueTokenRedisStoreRepository implements QueueTokenStoreRepositoryV2 {

    private final RedisTemplate<String, Integer> redisTemplate;

    @Override
    public QueueToken save(QueueToken createQueueToken) {
        redisTemplate.opsForZSet().add(RedisKey.REDIS_TICKETING_WAITING_QUEUE_KEY.getKeyName(), createQueueToken.getToken(), System.currentTimeMillis());
        Long rank = redisTemplate.opsForZSet().rank(RedisKey.REDIS_TICKETING_WAITING_QUEUE_KEY.getKeyName(), createQueueToken.getToken());
        createQueueToken.saveRank(rank);
        return createQueueToken;
    }
}
