package io.hhplus.tdd;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class LockHandler {

    private final Map<Long, Lock> lockMap = new ConcurrentHashMap<>();

    public void tryLock(Long userId) throws InterruptedException {
        Lock lock = lockMap.computeIfAbsent(userId, vLock -> new ReentrantLock());
        lock.tryLock(5, TimeUnit.SECONDS);
    }

    public void unlock(Long userId) {
        Lock lock = lockMap.get(userId);
        lock.unlock();
    }

    public <T> T lock(Long userId, Callable<T> method) throws Exception {
        tryLock(userId);

        try {
            return method.call();
        } finally {
            unlock(userId);
        }
    }
}
