package com.example.ticketing.config;

import lombok.Getter;

@Getter
public enum RedisKey {
    REDIS_TICKETING_WAITING_QUEUE_KEY("ticketing-waiting-queue"),
    REDIS_TICKETING_IN_PROGRESS_QUEUE_KEY("ticketing-in-progress-queue"),
    REDISSON_RLOCK_KEY_PREFIX("RLOCK_");

    private final String keyName;

    RedisKey(String keyName) {
        this.keyName = keyName;
    }
}
