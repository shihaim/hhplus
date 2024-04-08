package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.repository.ConcertSeatReaderRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ConcertSeatReaderImpl implements ConcertSeatReader {

    private final ConcertSeatReaderRepository readerRepository;

    public ConcertSeatReaderImpl(ConcertSeatReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public List<Seat> findConcertSeats(String concertCode, LocalDateTime concertDate) {
        return readerRepository.findAllByCodeAndDate(concertCode, concertDate);
    }
}
