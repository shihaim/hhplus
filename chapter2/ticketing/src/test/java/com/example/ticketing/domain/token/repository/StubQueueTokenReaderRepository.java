package com.example.ticketing.domain.token.repository;

import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

public class StubQueueTokenReaderRepository implements QueueTokenReaderRepository {
    @Override
    public Optional<QueueToken> findByUserUUID(String userUUID) {
        if (!userUUID.equals("1e9ebe68-045a-49f1-876e-a6ea6380dd5c")) return Optional.empty(); // fake 일까?

        String concertCode = "IU_BLUEMING_001";
        LocalDateTime expiredAt = LocalDateTime.of(2024, 4, 11, 13, 20, 35).plusMinutes(10);


        int token = userUUID.hashCode();
        token = 31 * token + expiredAt.hashCode();
        token = 31 * token + concertCode.hashCode();

        return Optional.of(
                QueueToken.builder()
                        .queueTokenId(1L)
                        .user(User.builder().userUUID("1e9ebe68-045a-49f1-876e-a6ea6380dd5c").build())
                        .concertCode(concertCode)
                        .token(token)
                        .status(QueueStatus.IN_PROGRESS)
                        .expiredAt(expiredAt)
                        .build()
        );
    }
}
