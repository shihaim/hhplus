package com.example.ticketing.domain.token.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueueTokenInfo {

    private String userUUID;
    private int token;
    private Long rank;

    /* Redis 저장용 대기열 토큰 정보 생성 */
    public static QueueTokenInfo createQueueTokenInfo(String concertCode, String userUUID) {
        int token = userUUID.hashCode();
        token = 31 * token + LocalDateTime.now().hashCode();
        token = 31 * token + concertCode.hashCode();

        return QueueTokenInfo.builder()
                .userUUID(userUUID)
                .token(token)
                .build();
    }

    /* Redis 조회용 대기열 토큰 정보 생성 */
    public static QueueTokenInfo findQueueTokenInfo(String userUUID, int token) {
        return QueueTokenInfo.builder()
                .userUUID(userUUID)
                .token(token)
                .build();
    }

    /* ZRANK */
    public void saveRank(Long rank) {
        this.rank = rank;
    }
}
