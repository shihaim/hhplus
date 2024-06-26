package com.example.ticketing.api.concert.dto;

import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.entity.QueueTokenInfo;

public record IssuedTokenResponse(
        Long tokenId,
        int token
) {

    /* RDBMS */
    public static IssuedTokenResponse convert(QueueToken queueToken) {
        return new IssuedTokenResponse(queueToken.getQueueTokenId(), queueToken.getToken());
    }

    /* Redis */
    public static IssuedTokenResponse convert2(QueueTokenInfo queueTokenInfo) {
        return new IssuedTokenResponse(queueTokenInfo.getRank(), queueTokenInfo.getToken());
    }
}
