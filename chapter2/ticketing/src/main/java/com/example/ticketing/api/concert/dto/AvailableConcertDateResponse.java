package com.example.ticketing.api.concert.dto;

import com.example.ticketing.domain.concert.entity.Concert;

import java.time.LocalDateTime;

public record AvailableConcertDateResponse(
        String concertCode,
        String concertName,
        LocalDateTime concertDate
) {

    public static AvailableConcertDateResponse convert(Concert concert) {
        return new AvailableConcertDateResponse(concert.getConcertPK().getConcertCode(), concert.getConcertName(), concert.getConcertPK().getConcertDate());
    }
}
