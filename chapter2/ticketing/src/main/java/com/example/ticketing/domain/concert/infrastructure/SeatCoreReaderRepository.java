package com.example.ticketing.domain.concert.infrastructure;

import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;
import com.example.ticketing.domain.concert.repository.SeatReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatCoreReaderRepository implements SeatReaderRepository {

    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Seat> findAllByCodeAndDate(String concertCode, LocalDateTime concertDate) {
        return null;
    }

    @Override
    public Optional<Seat> findByCodeAndDateAndStatus(String concertCode, LocalDateTime concertDate, int seatNumber, TicketingStatus status) {
        return Optional.empty();
    }
}
