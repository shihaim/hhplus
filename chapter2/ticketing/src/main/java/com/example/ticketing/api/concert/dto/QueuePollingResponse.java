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
}
