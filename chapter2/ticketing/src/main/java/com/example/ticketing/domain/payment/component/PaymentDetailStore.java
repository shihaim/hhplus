package com.example.ticketing.domain.payment.component;

import com.example.ticketing.domain.payment.entity.PaymentDetail;

public interface PaymentDetailStore {

    /**
     * 결제 요청 - 결제 내역 저장
     */
    PaymentDetail savePaymentDetail(PaymentDetail createPaymentDetail);
}
