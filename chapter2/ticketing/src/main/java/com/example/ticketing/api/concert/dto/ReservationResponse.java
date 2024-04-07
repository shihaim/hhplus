package com.example.ticketing.api.concert.dto;

public record ReservationResponse(
        String concertCode,
        String concertDate,
        int seatNumber,
        String assignedAt
) {
}
