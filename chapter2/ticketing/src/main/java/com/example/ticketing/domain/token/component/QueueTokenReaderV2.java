package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueTokenInfo;
import com.example.ticketing.domain.token.repository.QueueTokenReaderRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueueTokenReaderV2 {

    private final QueueTokenReaderRepositoryV2 readerRepository;

    /**
     * 나의 대기열 순번 조회
     */
    public Long findMyQueueRank(QueueTokenInfo queueTokenInfo, boolean isWaiting) {
        if (isWaiting) {
            return readerRepository.findMyRank(queueTokenInfo).getRank();
        }
        return -1L;
    }

    /**
     * In-Progress Count 조회
     */
    public int findInProgressTokenCount() {
        return readerRepository.findInProgressTokenCount().size();
    }

    /**
     * 진입 가능한 수 만큼 대기중인 토큰 꺼내기
     */
    public List<QueueTokenInfo> popWaitingTokens(int inProgressCount, int availableCount) {
        int count = availableCount - inProgressCount;
        if (count > 0){
            return readerRepository.popWaitingTokens(count);
        }
        return List.of();
    }
}
