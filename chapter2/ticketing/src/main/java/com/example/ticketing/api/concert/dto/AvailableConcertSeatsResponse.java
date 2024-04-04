package com.example.ticketing.api.concert.dto;

import com.example.ticketing.domain.concert.entity.TicketingStatus;

public record AvailableConcertSeatsResponse(
        String concertCode,
        String concertDate,
        int seatNumber,
        TicketingStatus status
) {
}
