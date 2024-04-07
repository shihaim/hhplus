package com.example.ticketing.api.payment.dto;

public record PaymentDetailResponse(
        String userUUID,
        String concertName,
        String concertDate,
        int seatNumber,
        int price
) {
}
