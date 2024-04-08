package com.example.ticketing.domain.concert.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Seat {
    private Long seatId;
    private Concert concert;
    private int seatNumber;
    private TicketingStatus status;
}
