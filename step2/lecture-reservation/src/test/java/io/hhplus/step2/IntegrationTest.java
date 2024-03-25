package io.hhplus.step2;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.exception.LectureReservationException;
import io.hhplus.step2.lecture.repository.component.LectureRepository;
import io.hhplus.step2.lecture.service.component.LectureReservationWriter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class IntegrationTest {

    @Autowired
    private LectureReservationWriter writer;

    @Autowired
    private LectureRepository lectureRepository;

    /**
     * [선착순 테스트]
     * case1: 100명의 수강생들이 동시에 특강을 신청
     * case2: 한명의 수강생이 5번 요청하고 다른한명의 수강생이 1번 수강신청을 요청. 단, 특강은 2개이며 6번의 요청을 동시에 실행
     */

    @Test
    @DisplayName("선착순테스트_100명의_수강생이_동시에_신청")
    void case1() {
        Lecture lecture = Lecture.builder()
                .lectureName("항해플러스 특강")
                .quantity(30)
                .openDate(LocalDateTime.now())
                .build();
        lectureRepository.save(lecture);

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < 100; i++) {
            final Long userId = i + 1L;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    writer.lectureReservation(userId, 1L, LocalDateTime.now());
                    successCount.incrementAndGet();
                } catch (LectureReservationException e) {
                    failCount.incrementAndGet();
                }
            }, executorService);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();

        Assertions.assertThat(successCount.get()).as("성공의 개수는 30").isEqualTo(30);
        Assertions.assertThat(failCount.get()).as("실패의 개수는 70").isEqualTo(70);
    }

    @Test
    @DisplayName("선착순테스트_한명의_수강생이_5번_요청하고_다른한명의_수강생이_수강신청")
    void case2() {
        // 특강이 2개만 존재
        Lecture lecture = Lecture.builder()
                .lectureName("항해플러스 특강")
                .quantity(2)
                .openDate(LocalDateTime.now())
                .build();
        lectureRepository.save(lecture);

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < 6; i++) {
            final Long userId1 = 1L;
            final Long userId2 = 2L;
            final int finalI = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    if (finalI != 5) {
                        writer.lectureReservation(userId1, 1L, LocalDateTime.now());
                    } else {
                        writer.lectureReservation(userId2, 1L, LocalDateTime.now());
                    }
                    successCount.incrementAndGet();
                } catch (LectureReservationException e) {
                    failCount.incrementAndGet();
                }
            }, executorService);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();

        Assertions.assertThat(successCount.get()).as("성공의 개수는 2").isEqualTo(2);
        Assertions.assertThat(failCount.get()).as("실패의 개수는 4").isEqualTo(4);
    }
}
