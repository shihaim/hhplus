package com.example.ticketing.domain.token.repository;

import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.user.entity.User;

import java.time.LocalDateTime;

public class StubQueueTokenStoreRepository implements QueueTokenStoreRepository {
    @Override
    public QueueToken save(QueueToken createQueueToken) {
        String concertCode = "IU_BLUEMING_001";
        String userUUID = "1e9ebe68-045a-49f1-876e-a6ea6380dd5c";
        LocalDateTime expiredAt = createQueueToken.getExpiredAt();

        int token = userUUID.hashCode();
        token = 31 * token + expiredAt.hashCode();
        token = 31 * token + concertCode.hashCode();


        return QueueToken.builder()
                .queueTokenId(1L)
                .user(User.builder().userUUID(userUUID).build())
                .concertCode(concertCode)
                .token(token)
                .status(QueueStatus.WAITING)
                .expiredAt(expiredAt)
                .build();
    }
}
