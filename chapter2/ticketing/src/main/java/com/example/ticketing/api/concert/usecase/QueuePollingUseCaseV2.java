package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.QueuePollingResponse;
import com.example.ticketing.domain.concert.component.ConcertSeatReader;
import com.example.ticketing.domain.concert.component.ConcertSeatValidator;
import com.example.ticketing.domain.concert.entity.TicketingStatus;
import com.example.ticketing.domain.token.component.QueueTokenReaderV2;
import com.example.ticketing.domain.token.component.QueueTokenStoreV2;
import com.example.ticketing.domain.token.entity.QueueTokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueuePollingUseCaseV2 {

    private final QueueTokenReaderV2 queueTokenReader;
    private final QueueTokenStoreV2 queueTokenStore;
    private final ConcertSeatReader concertSeatReader;
    private final ConcertSeatValidator concertSeatValidator;

    /**
     * 현재 대기열 순번 조회
     */
    public QueuePollingResponse execute(String concertCode, String userUUID, int token) {
        // 1. 예매 가능한 Seat 개수 조회
        int notCompletedSeatCount = concertSeatReader.findNotCompletedSeatCount(concertCode, TicketingStatus.NONE);
        concertSeatValidator.checkAvailableTicketingSeats(notCompletedSeatCount);
        // 2. In-Progress size 조회
        int inProgressTokenCount = queueTokenReader.findInProgressTokenCount();
        // 3. Waiting Set (3번 - 2번) 개수만큼 꺼내기
        List<QueueTokenInfo> findWaitingTokens = queueTokenReader.popWaitingTokens(inProgressTokenCount, notCompletedSeatCount);
        // 4. 꺼낸 토큰들 SETNX를 통해 In-Progress로 삽입 및 ttl 설정
        boolean isInProgress = queueTokenStore.inProgress(findWaitingTokens, userUUID, token);
        // 5. 대기열 진입 동기화와 동시에 현재 나의 대기열 확인
        Long myQueueRank = queueTokenReader.findMyQueueRank(QueueTokenInfo.findQueueTokenInfo(userUUID, token), isInProgress);

        return QueuePollingResponse.convert2(myQueueRank, token);
    }
}
