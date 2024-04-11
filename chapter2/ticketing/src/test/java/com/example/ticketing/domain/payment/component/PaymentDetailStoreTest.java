package com.example.ticketing.domain.payment.component;

import com.example.ticketing.domain.payment.entity.PaymentDetail;
import com.example.ticketing.domain.payment.repository.PaymentDetailStoreRepository;
import com.example.ticketing.domain.payment.repository.StubPaymentDetailStoreRepository;
import com.example.ticketing.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentDetailStoreTest {

    private final PaymentDetailStoreRepository stubStoreRepository = new StubPaymentDetailStoreRepository();
    private final PaymentDetailStore sut = new PaymentDetailStore(stubStoreRepository);

    /**
     * [결제 내역 저장]
     * cae1: 결제 내역 저장 성공
     */

    @Test
    @DisplayName("결제 내역 저장 성공")
    void case1() throws Exception {
        //given
        PaymentDetail createPaymentDetail = PaymentDetail.builder()
                .paymentDetailId(1L)
                .user(User.builder().userUUID("1e9ebe68-045a-49f1-876e-a6ea6380dd5c").build())
                .concertCode("IU_BLUEMING_001")
                .concertDate(LocalDateTime.of(2024, 4, 30, 15, 0, 0))
                .concertName("아이유 블루밍")
                .price(50000)
                .seatNumber(20)
                .build();

        //when
        PaymentDetail result = sut.savePaymentDetail(createPaymentDetail);

        //then
        assertThat(result.getPaymentDetailId()).isEqualTo(1L);
        assertThat(result.getUser().getUserUUID()).isEqualTo("1e9ebe68-045a-49f1-876e-a6ea6380dd5c");
        assertThat(result.getConcertCode()).isEqualTo("IU_BLUEMING_001");
        assertThat(result.getConcertDate()).isEqualTo(LocalDateTime.of(2024, 4, 30, 15, 0, 0));
        assertThat(result.getConcertName()).isEqualTo("아이유 블루밍");
        assertThat(result.getPrice()).isEqualTo(50000);
        assertThat(result.getSeatNumber()).isEqualTo(20);
    }

}