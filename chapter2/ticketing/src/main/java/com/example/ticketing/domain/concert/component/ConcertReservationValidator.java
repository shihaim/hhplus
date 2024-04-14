package com.example.ticketing.domain.concert.component;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ConcertReservationValidator {

    public void isExpired(LocalDateTime assignedAt) {
        if (assignedAt.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("임시 배정된 시간이 만료되었습니다.");
        }
    }

    /**
     * assignedAt이 현재 시간보다 이후 시간이라면 임시 배정할 수 없는 좌석
     */
    public void isAvailableReservation(LocalDateTime assignedAt) {
        if (assignedAt != null && assignedAt.isAfter(LocalDateTime.now())) {
            throw new RuntimeException("임시 배정을 진행할 수 없는 좌석입니다.");
        }
    }

}
