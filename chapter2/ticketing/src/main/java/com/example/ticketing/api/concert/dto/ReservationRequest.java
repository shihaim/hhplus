package com.example.ticketing.api.concert.dto;

public record ReservationRequest(
        String userUUID,
        String concertDate,
        int seatNumber
) {
}
