package com.example.ticketing.domain.concert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ConcertPK implements Serializable {

    @Column
    private String concertCode;
    @Column
    private LocalDateTime concertDate;

    public ConcertPK(String concertCode, LocalDateTime concertDate) {
        this.concertCode = concertCode;
        this.concertDate = concertDate;
    }
}
