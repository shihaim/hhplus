package com.example.ticketing.domain.token.infrastructure;

import com.example.ticketing.domain.token.entity.QueueToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueTokenJpaRepository extends JpaRepository<QueueToken, Long> {
}
