package com.example.ticketing.api.concert.dto;

import java.time.LocalDateTime;

public record FindConcertSeatsRequest(
        String userUUID,
        LocalDateTime concertDate
) {
}
