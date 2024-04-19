package com.example.ticketing.domain.concert.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @OneToOne(fetch = FetchType.LAZY)
    private Seat seat;

    @Column(name = "user_uuid")
    private String userUUID;

    @Column
    private int token;

    @Column
    @Enumerated(value = EnumType.STRING)
    private AssignmentStatus status;

    @Column
    private LocalDateTime assignedAt;

    @Version
    private Long version;

    @Builder
    public Reservation(Long reservationId, Seat seat, String userUUID, int token, AssignmentStatus status, LocalDateTime assignedAt, Long version) {
        this.reservationId = reservationId;
        this.seat = seat;
        this.userUUID = userUUID;
        this.token = token;
        this.status = status;
        this.assignedAt = assignedAt;
        this.version = version;
    }

    /**
     * 좌석 임시 배정
     */
    public LocalDateTime assignment(String userUUID, int token) {
        this.userUUID = userUUID;
        this.token = token;
        this.status = AssignmentStatus.ASSIGNED;
        return this.assignedAt = LocalDateTime.now().plusMinutes(2);
    }
}
