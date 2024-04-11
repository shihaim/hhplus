package com.example.ticketing.domain.concert.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Concert concert;
    @Column
    private int seatNumber;
    @Column
    @Enumerated(value = EnumType.STRING)
    private TicketingStatus status;

    @Builder
    public Seat(Long seatId, Concert concert, int seatNumber, TicketingStatus status) {
        this.seatId = seatId;
        this.concert = concert;
        this.seatNumber = seatNumber;
        this.status = status;
    }
}
