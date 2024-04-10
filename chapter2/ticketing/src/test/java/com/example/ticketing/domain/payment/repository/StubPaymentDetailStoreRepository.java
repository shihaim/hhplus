package com.example.ticketing.domain.payment.repository;

import com.example.ticketing.domain.payment.entity.PaymentDetail;
import com.example.ticketing.domain.user.entity.User;

import java.time.LocalDateTime;

public class StubPaymentDetailStoreRepository implements PaymentDetailStoreRepository {
    @Override
    public PaymentDetail save(PaymentDetail createPaymentDetail) {
        return PaymentDetail.builder()
                .paymentDetailId(1L)
                .user(User.builder().userUUID("1e9ebe68-045a-49f1-876e-a6ea6380dd5c").build())
                .concertCode("IU_BLUEMING_001")
                .concertDate(LocalDateTime.of(2024, 4, 30, 15, 0, 0))
                .concertName("아이유 블루밍")
                .price(50000)
                .seatNumber(20)
                .build();
    }
}
