package com.example.ticketing.domain.token.repository;

import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;

import java.util.Optional;

public interface QueueTokenReaderRepository {

    Optional<QueueToken> findByUserUUIDAndToken(String userUUID, int token);

    /**
     * IN_PROGRESS와 EXPIRED 사이의 max 값 조회하기(IN_PROGRESS가 없을 수도 있으므로)
     */
    long findLastQueueNumber(String concertCode, QueueStatus inProgress, QueueStatus expired);
}
