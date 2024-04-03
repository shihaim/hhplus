package com.example.ticketing.api.concert.dto;

public record IssuedTokenResponse(
        Long tokenId,
        int token
) {

    public static IssuedTokenResponse of(Long tokenId, int token) {
        return new IssuedTokenResponse(tokenId, token);
    }
}
