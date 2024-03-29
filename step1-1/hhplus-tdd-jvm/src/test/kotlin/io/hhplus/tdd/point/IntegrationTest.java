package io.hhplus.tdd.point;

import io.hhplus.tdd.point.repository.PointRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    private PointController pointController;

    @Autowired
    private PointRepository pointRepository;

    /**
     * case1: 동시성 테스트, 여러 건의 포인트 충전/사용 요청이 들어올 경우 순차적으로 처리
     * case2: 포인트 충전 성공 포인트 충전/이용 내역 저장 성공
     */

    @Test
    @DisplayName("동시성 테스트 - 여러 건의 포인트 충전/사용 요청이 들어올 경우 순차적으로 처리")
    void case1() throws Exception {
        //given
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        //when
        CompletableFuture<Void> futureCharge3000L = CompletableFuture.runAsync(() -> {
            try {
                pointController.charge(1L, 3000L);
                successCount.incrementAndGet();
            } catch (Exception e) {
                failCount.incrementAndGet();
            }
        }, service);
        futures.add(futureCharge3000L);

        CompletableFuture<Void> futureUse2000L = CompletableFuture.runAsync(() -> {
            try {
                pointController.use(1L, 2000L);
                successCount.incrementAndGet();
            } catch (Exception e) {
                failCount.incrementAndGet();
            }
        }, service);
        futures.add(futureUse2000L);

        CompletableFuture<Void> futureCharge5000L = CompletableFuture.runAsync(() -> {
            try {
                pointController.charge(1L, 5000L);
                successCount.incrementAndGet();
            } catch (Exception e) {
                failCount.incrementAndGet();
            }
        }, service);
        futures.add(futureCharge5000L);

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        service.shutdown();

        UserPoint userPoint = pointRepository.findUserPointById(1L);

        //then
        assertThat(userPoint.getPoint()).as("3000 -2000 +5000").isEqualTo(6000L);
    }

    @Test
    @DisplayName("포인트 충전 성공 포인트 충전/이용 내역 저장 성공")
    void case2() throws Exception {
        //given
        pointController.charge(1L, 2000L);
        pointController.use(1L, 1000L);

        //when
        List<PointHistory> history = pointController.history(1L);

        //then
        assertThat(history.size()).isEqualTo(2);

        assertThat(history.get(0).getAmount()).isEqualTo(2000L);
        assertThat(history.get(0).getType()).isEqualTo(TransactionType.CHARGE);

        assertThat(history.get(1).getAmount()).isEqualTo(1000L);
        assertThat(history.get(1).getType()).isEqualTo(TransactionType.USE);
    }
}
