package com.example.ticketing.server.interceptor.token.infrastructure;

import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QueueTokenVerificationJpaRepository extends JpaRepository<QueueToken, Long> {

    @Query("select count(qt) from QueueToken qt where qt.user.userUUID = :userUUID and qt.token = :token and qt.status != :status")
    int findVerificationToken(String userUUID, int token, QueueStatus status);
}
