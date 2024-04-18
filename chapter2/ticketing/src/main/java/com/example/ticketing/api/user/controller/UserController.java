package com.example.ticketing.api.user.controller;

import com.example.ticketing.api.concert.dto.ConcertApiResponse;
import com.example.ticketing.api.user.usecase.ChargeBalanceUseCase;
import com.example.ticketing.api.user.usecase.GetBalanceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final GetBalanceUseCase getBalanceUseCase;
    private final ChargeBalanceUseCase chargeBalanceUseCase;

    @GetMapping("/{userUUID}/balance")
    public ResponseEntity<ConcertApiResponse<?>> findUserBalance(
            @PathVariable("userUUID") String userUUID
    ) {
        int response = getBalanceUseCase.execute(userUUID);

        return ResponseEntity.ok(ConcertApiResponse.of(response));
    }

    @PatchMapping("/{userUUID}/balance")
    public ResponseEntity<ConcertApiResponse<?>> chargeUserBalance(
            @PathVariable("userUUID") String userUUID,
            @RequestBody int amount
    ) {
        int response = chargeBalanceUseCase.execute(userUUID, amount);

        return ResponseEntity.ok(ConcertApiResponse.of(response));
    }
}
