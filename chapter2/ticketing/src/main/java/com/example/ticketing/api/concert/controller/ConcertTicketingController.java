package com.example.ticketing.api.concert.controller;

import com.example.ticketing.api.concert.dto.AvailableConcertDateResponse;
import com.example.ticketing.api.concert.dto.ConcertApiResponse;
import com.example.ticketing.api.concert.dto.IssuedTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/concerts")
public class ConcertTicketingController {

    @PostMapping("/{concertCode}/token")
    public ResponseEntity<ConcertApiResponse<?>> issueToken(
            @PathVariable("concertCode") String concertCode,
            @RequestBody String userUUID
    ) {
        if (!"IU_BLUEMING_001".equals(concertCode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("존재하지 않는 콘서트 입니다!"));
        }

        if (!"1e9ebe68-045a-49f1-876e-a6ea6380dd5c".equals(userUUID)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("존재하지 않는 유저 입니다!"));
        }

        // DB에서 Token을 반환했음을 가정
        int result = LocalDateTime.of(2024, 4, 10, 13, 30, 0).hashCode();
        result = 31 * result + userUUID.hashCode();
        result = 31 * result + concertCode.hashCode();

        return ResponseEntity.ok(ConcertApiResponse.of(IssuedTokenResponse.of(1L, result)));
    }

    @GetMapping("/{concertCode}")
    public ResponseEntity<ConcertApiResponse<?>> findAvailableConcertDate(
            @PathVariable("concertCode") String concertCode,
            @RequestHeader("Authorization") int token,
            @RequestBody String userUUID
    ) {
        if (!"IU_BLUEMING_001".equals(concertCode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("존재하지 않는 콘서트 입니다!"));
        }

        if (!"1e9ebe68-045a-49f1-876e-a6ea6380dd5c".equals(userUUID)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("존재하지 않는 유저 입니다!"));
        }

        // DB에서 Token을 반환했음을 가정
        int findToken = LocalDateTime.of(2024, 4, 10, 13, 30, 0).hashCode();
        findToken = 31 * findToken + userUUID.hashCode();
        findToken = 31 * findToken + concertCode.hashCode();

        if (token != findToken) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("대기열 토큰이 일치하지 않습니다!"));
        }

        List<AvailableConcertDateResponse> result = List.of(
                new AvailableConcertDateResponse(),
                new AvailableConcertDateResponse(),
                new AvailableConcertDateResponse()
        );

        return ResponseEntity.ok(ConcertApiResponse.of(result));
    }
}
