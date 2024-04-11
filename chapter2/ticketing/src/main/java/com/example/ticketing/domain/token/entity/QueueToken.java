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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column
    private String concertCode;
    @Column
    private int token;
    @Column
    @Enumerated(value = EnumType.STRING)
    private QueueStatus status;
    @Column
    private LocalDateTime expiredAt;

    @Builder
    public QueueToken(Long queueTokenId, User user, String concertCode, int token, QueueStatus status, LocalDateTime expiredAt) {
        this.queueTokenId = queueTokenId;
        this.user = user;
        this.concertCode = concertCode;
        this.token = token;
        this.status = status;
        this.expiredAt = expiredAt;
    }

    public static QueueToken createQueueToken(String concertCode, User user) {
        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(10);

        int token = user.getUserUUID().hashCode();
        token = 31 * token + expiredAt.hashCode();
        token = 31 * token + concertCode.hashCode();

        return QueueToken.builder()
                .user(user)
                .concertCode(concertCode)
                .token(token)
                .status(QueueStatus.WAITING)
                .expiredAt(expiredAt)
                .build();
    }
}
