package com.example.ticketing.domain.concert.infrastructure;

import com.example.ticketing.domain.concert.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByConcert_ConcertPK(ConcertPK concertPK);

    Optional<Seat> findByConcert_ConcertPKAndSeatNumberAndStatus(ConcertPK concertPK, int seatNumber, TicketingStatus status);

    Integer findByConcert_ConcertPK_ConcertCodeAndStatus(String concertCode, TicketingStatus status);

    // Index 검증용 쿼리
    @Query("""
    select s from Seat s join fetch s.reservation
    """)
    List<Seat> findSeatList();

    @Query("""
    select s
      from Seat s join fetch s.concert c join fetch s.reservation
     where c.concertPK.concertCode = :concertCode
       and c.concertPK.concertDate = :concertDate
       and s.status = :status
    """)
    List<Seat> findSeatList(
            @Param("concertCode") String concertCode,
            @Param("concertDate") LocalDateTime concertDate,
            @Param("status") TicketingStatus status
    );

    @Query("""
    select s
      from Seat s join fetch s.concert c join fetch s.reservation
     where c.concertPK.concertCode = :concertCode
       and c.concertPK.concertDate = :concertDate
       and s.status in (:statusNone, :statusComplete)
    """)
    List<Seat> findSeatList(
            @Param("concertCode") String concertCode,
            @Param("concertDate") LocalDateTime concertDate,
            @Param("statusNone") TicketingStatus statusNone,
            @Param("statusComplete") TicketingStatus statusComplete
    );

    @Query("""
    select s
      from Seat s join fetch s.concert c join fetch s.reservation
     where c.concertPK.concertCode = :concertCode
       and c.concertPK.concertDate = :concertDate
    """)
    List<Seat> findSeatList(
            @Param("concertCode") String concertCode,
            @Param("concertDate") LocalDateTime concertDate
    );
}
