package com.example.ticketing.domain.payment.repository;

import com.example.ticketing.domain.payment.entity.PaymentDetail;

public interface PaymentDetailStoreRepository {

    PaymentDetail save(PaymentDetail createPaymentDetail);
}
