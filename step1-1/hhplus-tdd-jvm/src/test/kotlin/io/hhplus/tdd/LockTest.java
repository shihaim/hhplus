package io.hhplus.tdd;

import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.point.repository.PointRepository;
import io.hhplus.tdd.point.service.PointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class LockTest {

    @Autowired
    private PointRepository repository;
    @Autowired
    private PointService service;
    @Autowired
    private LockHandler lockHandler;

    @Test
    void lockTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                UserPoint lock = lockHandler.lock(1L, () -> service.charge(1L, 3000L));
            } catch (Exception e) {
            }
        }, executorService);
        futures.add(future2);

        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
            try {
                UserPoint lock = lockHandler.lock(1L, () -> service.charge(1L, 2000L));
            } catch (Exception e) {
            }
        }, executorService);
        futures.add(future3);

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                UserPoint lock = lockHandler.lock(1L, () -> service.use(1L, 4000L));
            } catch (Exception e) {

            }
        }, executorService);
        futures.add(future1);

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();

        UserPoint result = repository.findUserPointById(1L);
        System.out.println(result.getPoint());
    }
}
