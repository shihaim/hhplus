package com.example.ticketing.domain.payment.component;

import com.example.ticketing.domain.payment.entity.PaymentDetail;
import com.example.ticketing.domain.payment.repository.PaymentDetailStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentDetailStore {

    private final PaymentDetailStoreRepository storeRepository;

    /**
     * 결제 요청 - 결제 내역 저장
     */
    public PaymentDetail savePaymentDetail(PaymentDetail createPaymentDetail) {
        return storeRepository.save(createPaymentDetail);
    }
}
