package com.example.ticketing.domain.token.entity;

import com.example.ticketing.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QueueToken {
    private Long queueTokenId;
    private User user;
    private String concertCode;
    private int token;
    private QueueStatus status;
    private LocalDateTime expiredAt;
}
