package com.example.ticketing.api.payment.controller;

import com.example.ticketing.api.concert.dto.ConcertApiResponse;
import com.example.ticketing.api.payment.dto.PaymentDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostMapping
    public ResponseEntity<ConcertApiResponse<?>> payment(
            @RequestHeader("Authorization") int token,
            @RequestBody String userUUID
    ) {
        String concertDate = LocalDateTime.of(2024, 4, 20, 15, 0, 0).format(formatter);
        PaymentDetailResponse result = new PaymentDetailResponse(userUUID, "아이유 블루밍 콘서트", concertDate, 25, 50000);

        return ResponseEntity.ok(ConcertApiResponse.of(result));
    }
}
