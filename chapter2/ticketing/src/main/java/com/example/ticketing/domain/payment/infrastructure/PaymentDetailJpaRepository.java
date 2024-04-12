package com.example.ticketing.domain.payment.infrastructure;

import com.example.ticketing.domain.payment.entity.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailJpaRepository extends JpaRepository<PaymentDetail, Long> {
}
