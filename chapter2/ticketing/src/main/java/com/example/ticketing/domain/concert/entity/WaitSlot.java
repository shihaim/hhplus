package com.example.ticketing.domain.concert.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WaitSlot {

    private Long seatId;
    private String userUUID;
    private int token;
    private SlotStatus status;
    private LocalDateTime assignedAt;
    private long version;

    public WaitSlot(Long seatId, String userUUID, int token, SlotStatus status, LocalDateTime assignedAt, long version) {
        this.seatId = seatId;
        this.userUUID = userUUID;
        this.token = token;
        this.status = status;
        this.assignedAt = assignedAt;
        this.version = version;
    }
}
