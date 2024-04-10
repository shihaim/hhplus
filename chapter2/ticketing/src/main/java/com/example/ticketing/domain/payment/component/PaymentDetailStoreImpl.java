package com.example.ticketing.domain.payment.component;

import com.example.ticketing.domain.payment.entity.PaymentDetail;
import com.example.ticketing.domain.payment.repository.PaymentDetailStoreRepository;

public class PaymentDetailStoreImpl implements PaymentDetailStore {

    private final PaymentDetailStoreRepository storeRepository;

    public PaymentDetailStoreImpl(PaymentDetailStoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public PaymentDetail savePaymentDetail(PaymentDetail createPaymentDetail) {
        return storeRepository.save(createPaymentDetail);
    }
}
