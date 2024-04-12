package com.example.ticketing.domain.concert.infrastructure;

import com.example.ticketing.domain.concert.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
}
