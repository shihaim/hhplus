package com.example.ticketing.api.concert.dto;

import java.time.LocalDateTime;

public record ReservationResponse(
        String concertCode,
        LocalDateTime concertDate,
        int seatNumber,
        LocalDateTime assignedAt
) {
    public static ReservationResponse convert(String concertCode, LocalDateTime concertDate, int seatNumber, LocalDateTime assignedAt) {
        return new ReservationResponse(concertCode, concertDate, seatNumber, assignedAt);
    }
}
