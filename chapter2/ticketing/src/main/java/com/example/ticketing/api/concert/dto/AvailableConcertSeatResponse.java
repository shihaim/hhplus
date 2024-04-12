package com.example.ticketing.api.concert.dto;

import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;

import java.time.LocalDateTime;

public record AvailableConcertSeatResponse(
        String concertCode,
        LocalDateTime concertDate,
        int seatNumber,
        TicketingStatus status
) {

    public static AvailableConcertSeatResponse convert(Seat seat) {
        return new AvailableConcertSeatResponse(seat.getConcert().getConcertPK().getConcertCode(), seat.getConcert().getConcertPK().getConcertDate(), seat.getSeatNumber(), seat.getStatus());
    }
}
