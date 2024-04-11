package com.example.ticketing.api.concert.dto;

import com.example.ticketing.domain.token.entity.QueueToken;

public record IssuedTokenResponse(
        Long tokenId,
        int token
) {

    public static IssuedTokenResponse convert(QueueToken queueToken) {
        return new IssuedTokenResponse(queueToken.getQueueTokenId(), queueToken.getToken());
    }
    public static IssuedTokenResponse of(Long tokenId, int token) {
        return new IssuedTokenResponse(tokenId, token);
    }
}
