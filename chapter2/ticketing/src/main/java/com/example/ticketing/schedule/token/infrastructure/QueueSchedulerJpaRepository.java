package com.example.ticketing.schedule.token.infrastructure;

import com.example.ticketing.domain.concert.entity.TicketingStatus;
import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.schedule.token.entity.ConcertGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface QueueSchedulerJpaRepository extends JpaRepository<QueueToken, Long> {

    @Query("select c.concertPK.concertCode from Concert c group by c.concertPK.concertCode")
    List<ConcertGroup> findConcertGroup();

    @Query("select count(seat) from Seat seat where seat.concert.concertPK.concertCode = :concertCode and seat.status = :status")
    int findNotCompletedSeats(String concertCode, TicketingStatus status);

    @Query("select count(qt) from QueueToken qt where qt.concertCode = :concertCode and qt.status = :status")
    int findInProgressTokens(String concertCode, QueueStatus status);

    @Query("select qt from QueueToken qt where qt.concertCode = :concertCode and qt.status = :status")
    List<QueueToken> findWaitTokens(String concertCode, QueueStatus status);

    @Query("select qt from QueueToken qt where qt.expiredAt <= :now")
    List<QueueToken> findExpiredTokens(LocalDateTime now);
}
