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

}
