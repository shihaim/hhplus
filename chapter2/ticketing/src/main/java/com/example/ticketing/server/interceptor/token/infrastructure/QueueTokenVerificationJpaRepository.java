package com.example.ticketing.server.interceptor.token.infrastructure;

import com.example.ticketing.domain.token.entity.QueueToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QueueTokenVerificationJpaRepository extends JpaRepository<QueueToken, Long> {

    @Query("select qt from QueueToken qt where qt.user.userUUID = :userUUID")
    Optional<QueueToken> findVerificationToken(String userUUID);
}
