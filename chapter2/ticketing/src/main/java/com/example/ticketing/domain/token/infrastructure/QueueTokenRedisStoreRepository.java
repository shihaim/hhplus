package com.example.ticketing.domain.token.infrastructure;

import com.example.ticketing.config.RedisKey;
import com.example.ticketing.domain.token.entity.QueueTokenInfo;
import com.example.ticketing.domain.token.repository.QueueTokenStoreRepositoryV2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository("queueTokenRedisStoreRepository")
public class QueueTokenRedisStoreRepository implements QueueTokenStoreRepositoryV2 {

    private final RedisTemplate<String, QueueTokenInfo> redisTemplate;
    private final ZSetOperations<String, QueueTokenInfo> zSetOps;
    private final ValueOperations<String, QueueTokenInfo> valueOps;

    public QueueTokenRedisStoreRepository(RedisTemplate<String, QueueTokenInfo> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.zSetOps = redisTemplate.opsForZSet();
        this.valueOps = redisTemplate.opsForValue();
    }

    @Override
    public void save(QueueTokenInfo createQueueTokenInfo) {
        zSetOps.add(RedisKey.REDIS_TICKETING_WAITING_QUEUE_KEY.getKeyName(), createQueueTokenInfo, System.currentTimeMillis());
    }

    @Override
    public void inProgress(QueueTokenInfo waitingToken) {
        valueOps.setIfAbsent(RedisKey.REDIS_TICKETING_IN_PROGRESS_QUEUE_KEY.getKeyName() + ":" + waitingToken.getUserUUID() + ":" + waitingToken.getToken(), waitingToken, 3, TimeUnit.MINUTES);
    }
}
