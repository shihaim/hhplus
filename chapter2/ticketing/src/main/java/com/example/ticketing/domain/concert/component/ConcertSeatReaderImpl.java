package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;
import com.example.ticketing.domain.concert.repository.ConcertSeatReaderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ConcertSeatReaderImpl implements ConcertSeatReader {

    private final ConcertSeatReaderRepository readerRepository;

    public ConcertSeatReaderImpl(ConcertSeatReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public List<Seat> findAvailableSeats(String concertCode, LocalDateTime concertDate) {
        return readerRepository.findAllByCodeAndDate(concertCode, concertDate);
    }

    @Override
    public Optional<Seat> findNotCompletedSeat(String concertCode, LocalDateTime concertDate, int seatNumber) {
        return readerRepository.findByCodeAndDateAndStatus(concertCode, concertDate, seatNumber, TicketingStatus.NONE);
    }
}
