package com.example.ticketing.domain.token.entity;

import com.example.ticketing.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueueToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queueTokenId;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String concertCode;

    @Column
    private int token;

    @Column
    @Enumerated(value = EnumType.STRING)
    private QueueStatus status;

    @Column
    private LocalDateTime issuedAt;

    @Column
    private LocalDateTime expiredAt;

    @Transient
    private Long rank;

    @Builder
    public QueueToken(Long queueTokenId, User user, String concertCode, int token, QueueStatus status, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this.queueTokenId = queueTokenId;
        this.user = user;
        this.concertCode = concertCode;
        this.token = token;
        this.status = status;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    /* RDB 저장용 토큰 생성 */
    public static QueueToken createQueueToken(String concertCode, User user) {
        // TODO issuedAt의 사용 점이 애매모호하다. -> token 검증용으로 쓰려고 했던 것 같지만 사용되지 않고 있음.
        LocalDateTime issuedAt = LocalDateTime.now();

        int token = user.getUserUUID().hashCode();
        token = 31 * token + issuedAt.hashCode();
        token = 31 * token + concertCode.hashCode();

        return QueueToken.builder()
                .user(user)
                .concertCode(concertCode)
                .token(token)
                .status(QueueStatus.WAITING)
                .issuedAt(issuedAt)
                .build();
    }

    /* Redis 저장용 토큰 생성 */
    public static QueueToken createQueueToken(String concertCode, String userUUID) {
        int token = userUUID.hashCode();
        token = 31 * token + LocalDateTime.now().hashCode();
        token = 31 * token + concertCode.hashCode();

        return QueueToken.builder()
                .token(token)
                .build();
    }

    public void saveRank(Long rank) {
        this.rank = rank;
    }

    public void changeTokenToInProgress() {
        this.status = QueueStatus.IN_PROGRESS;
        this.expiredAt = LocalDateTime.now().plusMinutes(10);
    }

    public void changeTokenToExpired() {
        this.status = QueueStatus.EXPIRED;
    }
}
