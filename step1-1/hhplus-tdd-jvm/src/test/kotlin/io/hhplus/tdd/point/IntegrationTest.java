package io.hhplus.tdd.point;

import io.hhplus.tdd.point.repository.PointRepository;
import io.hhplus.tdd.point.service.PointService;
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
     * case2: 포인트 충전 성공 포인트 충전 내역 저장 성공
     * case3: 포인트 사용 성공 포인트 이용 내역 저장 성공
     */

    @Test
    @DisplayName("")
    void case1() throws Exception {
        //given
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        //when
        CompletableFuture<Void> future3000L = CompletableFuture.runAsync(() -> {
            try {
                pointController.charge(1L, 3000L);
                successCount.incrementAndGet();
            } catch (Exception e) {
                failCount.incrementAndGet();
            }
        }, service);
        futures.add(future3000L);
        CompletableFuture<Void> future5000L = CompletableFuture.runAsync(() -> {
            try {
                pointController.charge(1L, 5000L);
                successCount.incrementAndGet();
            } catch (Exception e) {
                failCount.incrementAndGet();
            }
        }, service);
        futures.add(future5000L);

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        service.shutdown();

        UserPoint userPoint = pointRepository.findUserPointById(1L);
        System.out.println(userPoint.getPoint());

        //then
        assertThat(userPoint.getPoint()).as("동시에 3000원과 5000원으로 충전 요청이 들어오면 총 8000원이여야 함.").isEqualTo(8000L);
    }

    @Test
    @DisplayName("")
    void case2() throws Exception {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("")
    void case3() throws Exception {
        //given

        //when

        //then
    }
}
