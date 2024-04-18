package com.example.ticketing.api.concert.dto;

import java.time.LocalDateTime;

public record ReservationRequest(
        String userUUID,
        LocalDateTime concertDate,
        int seatNumber
) {
}
