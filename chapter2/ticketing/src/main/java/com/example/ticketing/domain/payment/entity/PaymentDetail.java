package com.example.ticketing.domain.payment.entity;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.entity.Reservation;
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
public class PaymentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String concertCode;

    @Column
    private LocalDateTime concertDate;

    @Column
    private String concertName;

    @Column
    private int price;

    @Column
    private int seatNumber;

    @Builder
    public PaymentDetail(Long paymentDetailId, User user, String concertCode, LocalDateTime concertDate, String concertName, int price, int seatNumber) {
        this.paymentDetailId = paymentDetailId;
        this.user = user;
        this.concertCode = concertCode;
        this.concertDate = concertDate;
        this.concertName = concertName;
        this.price = price;
        this.seatNumber = seatNumber;
    }

    public static PaymentDetail createPaymentDetail(User user, Concert concert, Reservation reservation) {
        return PaymentDetail.builder()
                .user(user)
                .concertCode(concert.getConcertPK().getConcertCode())
                .concertDate(concert.getConcertPK().getConcertDate())
                .concertName(concert.getConcertName())
                .price(concert.getPrice())
                .seatNumber(reservation.getSeat().getSeatNumber())
                .build();
    }
}
