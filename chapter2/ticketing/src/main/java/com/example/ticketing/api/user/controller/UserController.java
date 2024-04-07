package com.example.ticketing.api.user.controller;

import com.example.ticketing.api.concert.dto.ConcertApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/{userUUID}/balance")
    public ResponseEntity<ConcertApiResponse<?>> findUserBalance(
            @PathVariable("userUUID") String userUUID
    ) {

        return ResponseEntity.ok(ConcertApiResponse.of(1000));
    }

    @PatchMapping("/{userUUID}/balance")
    public ResponseEntity<ConcertApiResponse<?>> chargeUserBalance(
            @PathVariable("userUUID") String userUUID,
            @RequestBody int amount
    ) {

        return ResponseEntity.ok(ConcertApiResponse.of(51000));
    }
}
