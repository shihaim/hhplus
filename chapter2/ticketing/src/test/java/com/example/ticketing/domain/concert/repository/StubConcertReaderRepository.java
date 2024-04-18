package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.entity.ConcertPK;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class StubConcertReaderRepository implements ConcertReaderRepository {
    @Override
    public List<Concert> findAllByConcertCode(String concertCode) {
        if (concertCode == null) return List.of(); // fake??

        return List.of(
                Concert.builder().build(),
                Concert.builder().build(),
                Concert.builder().build()
        );
    }

    @Override
    public Optional<Concert> findByConcertPK(ConcertPK concertPK) {
        return Optional.of(Concert.builder().price(50000).build());
    }
}
