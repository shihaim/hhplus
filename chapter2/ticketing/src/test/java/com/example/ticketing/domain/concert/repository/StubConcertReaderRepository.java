package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.Concert;

import java.util.List;

public class StubConcertReaderRepository implements ConcertReaderRepository {
    @Override
    public List<Concert> findAllByConcertCode(String concertCode) {
        return List.of(
                Concert.builder().build(),
                Concert.builder().build(),
                Concert.builder().build()
        );
    }
}
