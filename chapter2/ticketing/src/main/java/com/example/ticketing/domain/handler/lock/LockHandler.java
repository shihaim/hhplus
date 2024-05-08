package com.example.ticketing.domain.handler.lock;

import com.example.ticketing.config.RedisKey;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class LockHandler {

    private final RedissonClient redissonClient;

    public <T> T runOnLock(String key, Long waitTime, Long leaseTime, Supplier<T> execute) {
        RLock lock = redissonClient.getLock(RedisKey.REDISSON_RLOCK_KEY_PREFIX.getKeyName() + key);

        try {
            lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            return execute.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                lock.unlock();
            } catch (IllegalMonitorStateException e) {
                log.info("Redisson Lock Already UnLock {}", RedisKey.REDISSON_RLOCK_KEY_PREFIX.getKeyName() + key);
            }
        }
    }

    public <T> T runOnLock(String key, Long waitTime, Long leaseTime, String errMsg, Supplier<T> execute) {
        RLock lock = redissonClient.getLock(RedisKey.REDISSON_RLOCK_KEY_PREFIX.getKeyName() + key);

        try {
            if (!lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)) {
                throw new OptimisticLockException(errMsg);
            }
            return execute.get();
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                lock.unlock();
            } catch (IllegalMonitorStateException e) {
                log.info("Redisson Lock Already UnLock {}", RedisKey.REDISSON_RLOCK_KEY_PREFIX.getKeyName() + key);
            }
        }
    }
}
