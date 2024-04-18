package com.example.ticketing.api.payment.controller;

import com.example.ticketing.api.concert.dto.ConcertApiResponse;
import com.example.ticketing.api.payment.dto.PaymentDetailResponse;
import com.example.ticketing.api.payment.usecase.PayUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PayUseCase payUseCase;

    @PostMapping
    public ResponseEntity<ConcertApiResponse<?>> payment(
            @RequestHeader("Authorization") int token,
            @RequestBody String userUUID
    ) {
        PaymentDetailResponse response = payUseCase.execute(userUUID);

        return ResponseEntity.ok(ConcertApiResponse.of(response));
    }
}
