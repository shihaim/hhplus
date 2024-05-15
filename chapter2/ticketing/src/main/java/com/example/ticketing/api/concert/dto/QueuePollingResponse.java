package com.example.ticketing.api.concert.dto;

import com.example.ticketing.domain.token.entity.QueueStatus;

public record QueuePollingResponse(
        Long tokenId,
        int token,
        QueueStatus status
) {

    public static QueuePollingResponse convert(long tokenId, int token, QueueStatus status) {
        return new QueuePollingResponse(tokenId, token, status);
    }

    public static QueuePollingResponse convert2(Long rank, int token) {
        return new QueuePollingResponse(rank, token, rank == -1L ? QueueStatus.IN_PROGRESS : QueueStatus.WAITING);
    }
}
