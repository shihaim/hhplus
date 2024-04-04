package com.example.ticketing.api.user.controller;

import com.example.ticketing.api.concert.dto.ConcertApiResponse;
import com.example.ticketing.api.concert.dto.IssuedTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/{userUUID}/balance")
    public ResponseEntity<ConcertApiResponse<?>> findUserBalance(
            @PathVariable("userUUID") String userUUID
    ) {
        if (!"1e9ebe68-045a-49f1-876e-a6ea6380dd5c".equals(userUUID)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("존재하지 않는 유저 입니다!"));
        }

        return ResponseEntity.ok(ConcertApiResponse.of(1000));
    }

    @PatchMapping("/{userUUID}/balance")
    public ResponseEntity<ConcertApiResponse<?>> chargeUserBalance(
            @PathVariable("userUUID") String userUUID,
            @RequestBody int amount
    ) {
        if (!"1e9ebe68-045a-49f1-876e-a6ea6380dd5c".equals(userUUID)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("존재하지 않는 유저 입니다!"));
        }

        if (amount < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("충전 금액이 음수일 수 없습니다!"));
        }

        return ResponseEntity.ok(ConcertApiResponse.of(51000));
    }
}
