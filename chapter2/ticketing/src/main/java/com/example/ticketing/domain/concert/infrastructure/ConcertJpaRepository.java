package com.example.ticketing.domain.concert.infrastructure;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.entity.ConcertPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<Concert, ConcertPK> {
}
