package com.example.ticketing.domain.concert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Concert {

    @EmbeddedId
    private ConcertPK concertPK;

    @Column
    private String concertName;

    @Column
    private int price;

    @Builder
    public Concert(String concertCode, LocalDateTime concertDate, String concertName, int price) {
        this.concertPK = ConcertPK.of(concertCode, concertDate);
        this.concertName = concertName;
        this.price = price;
    }
}
