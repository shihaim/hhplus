package com.example.ticketing.domain.concert.infrastructure;

import com.example.ticketing.domain.concert.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {
}
