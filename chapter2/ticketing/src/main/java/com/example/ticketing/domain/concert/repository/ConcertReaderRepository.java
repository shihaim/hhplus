package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.Concert;

import java.util.List;

public interface ConcertReaderRepository {

    List<Concert> findAllByConcertCode(String concertCode);
}
