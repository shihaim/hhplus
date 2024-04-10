package com.example.ticketing.domain.payment.entity;

import com.example.ticketing.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentDetail {
    private Long paymentDetailId;
    private User user;
    private String concertCode;
    private LocalDateTime concertDate;
    private String concertName;
    private int price;
    private int seatNumber;
}
