package com.example.ticketing.api.payment.dto;

import com.example.ticketing.domain.payment.entity.PaymentDetail;

import java.time.LocalDateTime;

public record PaymentDetailResponse(
        String userUUID,
        String concertName,
        LocalDateTime concertDate,
        int seatNumber,
        int price
) {
    public static PaymentDetailResponse convert(PaymentDetail paymentDetail) {
        return new PaymentDetailResponse(
                paymentDetail.getUser().getUserUUID(),
                paymentDetail.getConcertName(),
                paymentDetail.getConcertDate(),
                paymentDetail.getSeatNumber(),
                paymentDetail.getPrice()
        );
    }
}
