package com.example.ticketing.server.schedule.token.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertGroup {
    private String concertCode;
}
