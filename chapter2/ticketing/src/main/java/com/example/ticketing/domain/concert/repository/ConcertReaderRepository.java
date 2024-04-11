package com.example.ticketing.domain.concert.repository;

import com.example.ticketing.domain.concert.entity.Concert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConcertReaderRepository {

    List<Concert> findAllByConcertCode(String concertCode);

    Optional<Concert> findByConcertCodeAndDate(String concertCode, LocalDateTime concertDate);
}
