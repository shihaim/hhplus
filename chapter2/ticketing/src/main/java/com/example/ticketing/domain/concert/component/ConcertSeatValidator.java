package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Seat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConcertSeatValidator {

    public void isExists(List<Seat> seats, int seatNumber) {
        boolean exists = seats.stream().anyMatch(s -> s.getSeatNumber() == seatNumber);
        if (!exists) {
            throw new IllegalArgumentException("존재하지 않는 좌석입니다");
        }
    }
}
