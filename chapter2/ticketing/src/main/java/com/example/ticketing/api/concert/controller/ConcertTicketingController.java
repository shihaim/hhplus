package com.example.ticketing.api.concert.controller;

import com.example.ticketing.api.concert.dto.*;
import com.example.ticketing.domain.concert.entity.TicketingStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/concerts")
public class ConcertTicketingController {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
    public ResponseEntity<ConcertApiResponse<?>> findAvailableConcertDates(
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

        List<AvailableConcertDatesResponse> result = List.of(
                new AvailableConcertDatesResponse(concertCode, "아이유 블루밍 콘서트",
                        LocalDateTime.of(2024, 4, 20, 15, 0, 0).format(formatter)),
                new AvailableConcertDatesResponse(concertCode, "아이유 블루밍 콘서트",
                        LocalDateTime.of(2024, 4, 21, 15, 0, 0).format(formatter))
        );

        return ResponseEntity.ok(ConcertApiResponse.of(result));
    }

    @GetMapping("/{concertCode}/seats")
    public ResponseEntity<ConcertApiResponse<?>> findAvailableConcertSeats(
            @PathVariable("concertCode") String concertCode,
            @RequestHeader("Authorization") int token,
            @RequestBody FindConcertSeatsRequest requestDto
    ) {
        if (!"IU_BLUEMING_001".equals(concertCode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("존재하지 않는 콘서트 입니다!"));
        }

        if (!"1e9ebe68-045a-49f1-876e-a6ea6380dd5c".equals(requestDto.userUUID())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("존재하지 않는 유저 입니다!"));
        }

        // DB에서 Token을 반환했음을 가정
        int findToken = LocalDateTime.of(2024, 4, 10, 13, 30, 0).hashCode();
        findToken = 31 * findToken + requestDto.userUUID().hashCode();
        findToken = 31 * findToken + concertCode.hashCode();

        if (token != findToken) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConcertApiResponse.of("대기열 토큰이 일치하지 않습니다!"));
        }

        LocalDateTime availableConcertDate = LocalDateTime.of(2024, 4, 20, 15, 0, 0);
        List<AvailableConcertSeatsResponse> result;
        if (availableConcertDate.isEqual(requestDto.concertDate())) {
            result = List.of(
                    new AvailableConcertSeatsResponse(concertCode, availableConcertDate.format(formatter), 5, TicketingStatus.NONE),
                    new AvailableConcertSeatsResponse(concertCode, availableConcertDate.format(formatter), 10, TicketingStatus.NONE),
                    new AvailableConcertSeatsResponse(concertCode, availableConcertDate.format(formatter), 22, TicketingStatus.NONE),
                    new AvailableConcertSeatsResponse(concertCode, availableConcertDate.format(formatter), 41, TicketingStatus.NONE),
                    new AvailableConcertSeatsResponse(concertCode, availableConcertDate.format(formatter), 48, TicketingStatus.NONE)
            );
        } else {
            result = List.of();
        }

        return ResponseEntity.ok(ConcertApiResponse.of(result));
    }
}
