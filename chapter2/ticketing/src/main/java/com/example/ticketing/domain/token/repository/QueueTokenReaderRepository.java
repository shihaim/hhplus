package com.example.ticketing.domain.token.repository;

import com.example.ticketing.domain.token.entity.QueueToken;

import java.util.Optional;

public interface QueueTokenReaderRepository {

    Optional<QueueToken> findByUserUUID(String userUUID);
}
