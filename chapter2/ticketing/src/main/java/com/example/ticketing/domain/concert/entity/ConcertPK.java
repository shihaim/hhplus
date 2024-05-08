package com.example.ticketing.domain.concert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ConcertPK implements Serializable {

    @Column(name = "concert_code")
    private String concertCode;
    @Column(name = "concert_date")
    private LocalDateTime concertDate;

    public static ConcertPK of(String concertCode, LocalDateTime concertDate) {
        return new ConcertPK(concertCode, concertDate);
    }
}
