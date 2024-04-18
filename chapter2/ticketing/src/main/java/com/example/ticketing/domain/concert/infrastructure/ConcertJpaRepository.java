package com.example.ticketing.domain.concert.infrastructure;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.entity.ConcertPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<Concert, ConcertPK> {

    @Query("select c from Concert c where c.concertPK.concertCode = :concertCode")
    List<Concert> findAllByConcertCode(String concertCode);
}
