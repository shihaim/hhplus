package com.example.ticketing.domain.payment.infrastructure;

import com.example.ticketing.domain.payment.entity.PaymentDetail;
import com.example.ticketing.domain.payment.repository.PaymentDetailStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentCoreStoreRepository implements PaymentDetailStoreRepository {

    private final PaymentDetailJpaRepository paymentDetailJpaRepository;

    @Override
    public PaymentDetail save(PaymentDetail createPaymentDetail) {
        return paymentDetailJpaRepository.save(createPaymentDetail);
    }
}
