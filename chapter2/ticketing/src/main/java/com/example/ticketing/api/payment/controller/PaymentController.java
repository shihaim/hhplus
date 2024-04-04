package com.example.ticketing.api.payment.controller;

import com.example.ticketing.api.concert.dto.ConcertApiResponse;
import com.example.ticketing.api.payment.usecase.PaymentAssignedSeatUseCase;
import com.example.ticketing.domain.concert.entity.SlotStatus;
import com.example.ticketing.domain.concert.entity.WaitSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentAssignedSeatUseCase paymentAssignedSeatUseCase;

    @PostMapping
    public ResponseEntity<ConcertApiResponse<?>> payment(
            @RequestHeader("Authorization") int token,
            @RequestBody String userUUID
    ) {
        if (!"1e9ebe68-045a-49f1-876e-a6ea6380dd5c".equals(userUUID)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("존재하지 않는 유저 입니다!"));
        }

        // DB에서 Token을 반환했음을 가정
        int findToken = LocalDateTime.of(2024, 4, 10, 13, 30, 0).hashCode();
        findToken = 31 * findToken + userUUID.hashCode();
        findToken = 31 * findToken + "IU_BLUEMING_001".hashCode();

        if (token != findToken) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("대기열 토큰이 일치하지 않습니다!"));
        }

        // DB에서 현재 유저에게 임시 배정된 좌석을 반환했음을 가정
        WaitSlot findWaitSlot = new WaitSlot(35L, "1e9ebe68-045a-49f1-876e-a6ea6380dd5c", findToken, SlotStatus.NOT_ASSIGNED,
                LocalDateTime.of(2024, 4, 4, 10, 33, 0), 1L);
        if (LocalDateTime.now().minusSeconds(1).isAfter(findWaitSlot.getAssignedAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("임시 배정된 시간이 만료되었습니다!"));
        };

        return null;
    }
}
