package com.example.ticketing.domain.token.infrastructure;

import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QueueTokenJpaRepository extends JpaRepository<QueueToken, Long> {

    Optional<QueueToken> findByUser_UserUUIDAndToken(@Param("userUUID") String userUUID, @Param("token") int token);

    @Query("select max(qt.queueTokenId) from QueueToken qt where qt.concertCode = :concertCode and (qt.status = :inProgress or qt.status = :expired)")
    Optional<Long> findLastQueueNumber(String concertCode, QueueStatus inProgress, QueueStatus expired);
}
