package com.example.ticketing.api.concert.dto;

public record AvailableConcertDatesResponse(
        String concertCode,
        String concertName,
        String concertDate
) {
}
