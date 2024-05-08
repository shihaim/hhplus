package com.example.ticketing.domain.concert.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "seat")
public class Seat {

    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "concert_code", referencedColumnName = "concert_code"),
            @JoinColumn(name = "concert_date", referencedColumnName = "concert_date"),
    })
    private Concert concert;

    @Column
    private int seatNumber;

    @Column
    @Enumerated(value = EnumType.STRING)
    private TicketingStatus status;

    @OneToOne(mappedBy = "seat", fetch = FetchType.LAZY)
    private Reservation reservation;

    @Builder
    public Seat(Long seatId, Concert concert, int seatNumber, TicketingStatus status) {
        this.seatId = seatId;
        this.concert = concert;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public void ticketingComplete() {
        this.status = TicketingStatus.COMPLETE;
    }
}
