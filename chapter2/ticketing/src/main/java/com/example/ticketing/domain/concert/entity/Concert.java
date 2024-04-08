package com.example.ticketing.domain.concert.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class Concert {
    private String concertCode;
    private LocalDateTime concertDate;
    private String concertName;
    private int price;
}
