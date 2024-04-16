package com.example.ticketing.server.schedule.token.component;

import com.example.ticketing.domain.concert.entity.TicketingStatus;
import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.server.schedule.token.entity.ConcertGroup;
import com.example.ticketing.server.schedule.token.infrastructure.QueueSchedulerJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class QueueSchedulerComponent {

    private final QueueSchedulerJpaRepository repository;

    /**
     * TODO [통합 테스트]
     * [대기열 순번 Scheduler]
     * 1. 예매가 안된 좌석(콘서트 예약 테이블과는 무관) Count를 조회
     * 2. 토큰 상태가 IN_PROGRESS인 데이터 Count 조회
     * 3. (1번 - 2번)값이 양수인 경우 Wait의 상태의 토큰 대기열 상태와 유효 시간 변경
     */
//    @Scheduled(cron = "5/5 * * * * ?")
    public void queueNumberScheduleTask() {
        log.info("queueNumberScheduleTask start");
        List<ConcertGroup> concertGroup = repository.findConcertGroup();

        concertGroup.stream()
                .forEach(group -> {
                    String concertCode = group.getConcertCode();
                    int notCompletedCount = repository.findNotCompletedSeats(concertCode, TicketingStatus.NONE);
                    int inProgressCount = repository.findInProgressTokens(concertCode, QueueStatus.IN_PROGRESS);
                    int changeCount = notCompletedCount - inProgressCount;
                    if (changeCount > 0) {
                        List<QueueToken> waitTokens = repository.findWaitTokens(concertCode, QueueStatus.WAITING);
                        waitTokens.stream().limit(changeCount).forEach(QueueToken::changeTokenToInProgress);
                    }
                });
        log.info("queueNumberScheduleTask end");
    }

    /**
     * TODO [통합 테스트]
     * [대기열 만료 Scheduler]
     * 만료된 토큰을 삭제
     */
//    @Scheduled(cron = "10/10 * * * * ?")
    public void queueExpirationScheduleTask() {
        log.info("queueExpirationScheduleTask start");
        List<QueueToken> expiredTokens = repository.findExpiredTokens(LocalDateTime.now());
        repository.deleteAll(expiredTokens);
        log.info("queueExpirationScheduleTask end");
    }
}

