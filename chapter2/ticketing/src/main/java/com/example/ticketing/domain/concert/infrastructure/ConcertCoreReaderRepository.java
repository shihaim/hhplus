package com.example.ticketing.domain.concert.infrastructure;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.repository.ConcertReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertCoreReaderRepository implements ConcertReaderRepository {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public List<Concert> findAllByConcertCode(String concertCode) {
        return null;
    }

    @Override
    public Optional<Concert> findByConcertCodeAndDate(String concertCode, LocalDateTime concertDate) {
        return Optional.empty();
    }
}
