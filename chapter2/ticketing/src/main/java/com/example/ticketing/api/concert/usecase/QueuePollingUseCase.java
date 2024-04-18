package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.QueuePollingResponse;
import com.example.ticketing.domain.token.component.QueueTokenReader;
import com.example.ticketing.domain.token.entity.QueueToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueuePollingUseCase {

    private final QueueTokenReader queueTokenReader;

    /**
     * 현재 대기열 순번 조회
     */
    public QueuePollingResponse execute(String concertCode, String userUUID, int token) {
        QueueToken findQueueToken = queueTokenReader.findQueueToken(userUUID, token);
        long findLastQueueNumber = queueTokenReader.findLastEnteredToken(concertCode, findQueueToken.getQueueTokenId(), findQueueToken.getStatus());

        return QueuePollingResponse.convert(findLastQueueNumber, findQueueToken.getToken(), findQueueToken.getStatus());
    }
}
