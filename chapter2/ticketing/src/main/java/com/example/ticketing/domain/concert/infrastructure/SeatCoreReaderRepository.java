package com.example.ticketing.domain.concert.infrastructure;

import com.example.ticketing.domain.concert.entity.ConcertPK;
import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;
import com.example.ticketing.domain.concert.repository.SeatReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatCoreReaderRepository implements SeatReaderRepository {

    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Seat> findAllByConcertPk(ConcertPK concertPK) {
        return seatJpaRepository.findAllByConcert_ConcertPK(concertPK);
    }

    @Override
    public Optional<Seat> findByConcertPKAndSeatNumberAndStatus(ConcertPK concertPK, int seatNumber, TicketingStatus status) {
        return seatJpaRepository.findByConcert_ConcertPKAndSeatNumberAndStatus(concertPK, seatNumber, status);
    }

    @Override
    public Integer findNotCompletedSeatCount(String concertCode, TicketingStatus status) {
        return seatJpaRepository.findByConcert_ConcertPK_ConcertCodeAndStatus(concertCode, status);
    }
}
