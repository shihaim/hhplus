package com.example.ticketing.domain.concert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Concert {

    @Id
    private String concertCode;
    @Id
    private LocalDateTime concertDate;
    @Column
    private String concertName;
    @Column
    private int price;

    @Builder
    public Concert(String concertCode, LocalDateTime concertDate, String concertName, int price) {
        this.concertCode = concertCode;
        this.concertDate = concertDate;
        this.concertName = concertName;
        this.price = price;
    }
}
