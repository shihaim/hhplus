package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.repository.ConcertReaderRepository;

import java.util.List;

public class ConcertReaderImpl implements ConcertReader {

    private final ConcertReaderRepository readerRepository;

    public ConcertReaderImpl(ConcertReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public List<Concert> findConcertDates(String concertCode) {
        return readerRepository.findAllByConcertCode(concertCode);
    }
}
