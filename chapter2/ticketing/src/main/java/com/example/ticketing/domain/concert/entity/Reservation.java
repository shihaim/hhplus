package com.example.ticketing.domain.concert.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Reservation {

    private Long reservationId;
    private Seat seat;
    private String userUUID;
    private int token;
    private AssignmentStatus status;
    private LocalDateTime assignedAt;
    private int version;

    public void assignment(String userUUID, int token) {
        this.userUUID = userUUID;
        this.token = token;
        this.status = AssignmentStatus.ASSIGNED;
        this.assignedAt = LocalDateTime.now().plusMinutes(5);
    }

    public void increaseVersion() {
        this.version += 1;
    }
}
