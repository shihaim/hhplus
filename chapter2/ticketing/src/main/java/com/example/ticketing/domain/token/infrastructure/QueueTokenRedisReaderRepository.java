package com.example.ticketing.domain.token.infrastructure;

import com.example.ticketing.config.RedisKey;
import com.example.ticketing.domain.token.entity.QueueTokenInfo;
import com.example.ticketing.domain.token.repository.QueueTokenReaderRepositoryV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository("queueTokenRedisReaderRepository")
public class QueueTokenRedisReaderRepository implements QueueTokenReaderRepositoryV2 {

    private final RedisTemplate<String, QueueTokenInfo> redisTemplate;
    private final ZSetOperations<String, QueueTokenInfo> zSetOps;

    public QueueTokenRedisReaderRepository(RedisTemplate<String, QueueTokenInfo> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.zSetOps = redisTemplate.opsForZSet();
    }

    @Override
    public QueueTokenInfo findMyRank(QueueTokenInfo queueTokenInfo) {
        Long rank = zSetOps.rank(RedisKey.REDIS_TICKETING_WAITING_QUEUE_KEY.getKeyName(), queueTokenInfo);
        queueTokenInfo.saveRank(rank);
        return queueTokenInfo;
    }

    @Override
    public Set<String> findInProgressTokenCount() {
        Set<String> keys = new HashSet<>();
        ScanOptions scanOptions = ScanOptions.scanOptions().match(RedisKey.REDIS_TICKETING_IN_PROGRESS_QUEUE_KEY.getKeyName() + "*").count(1000).build();
        try (Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(scanOptions)) {
            while (cursor.hasNext()) {
                keys.add(new String(cursor.next()));
            }
            return keys;
        } catch (NullPointerException e) {
            log.warn("findInProgressTokenCount :: NullPointerException");
            e.printStackTrace();
        }
        return keys;
    }

    @Override
    public List<QueueTokenInfo> popWaitingTokens(long count) {
        try {
            return zSetOps.popMin(RedisKey.REDIS_TICKETING_WAITING_QUEUE_KEY.getKeyName(), count).stream()
                    .map(ZSetOperations.TypedTuple::getValue)
                    .toList();
        } catch (NullPointerException e) {
            log.warn("popWaitingTokens :: NullPointerException");
            throw new RuntimeException(e);
        }
    }
}
