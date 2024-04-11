package com.example.ticketing.api.concert.controller;

import com.example.ticketing.api.concert.dto.*;
import com.example.ticketing.domain.concert.entity.TicketingStatus;
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

        int token = LocalDateTime.of(2024, 4, 10, 13, 30, 0).hashCode();
        token = 31 * token + userUUID.hashCode();
        token = 31 * token + concertCode.hashCode();

        IssuedTokenResponse result = IssuedTokenResponse.of(1L, token);

        return ResponseEntity.ok(ConcertApiResponse.of(result));
    }

    @GetMapping("/{concertCode}")
    public ResponseEntity<ConcertApiResponse<?>> findAvailableConcertDates(
            @PathVariable("concertCode") String concertCode,
            @RequestHeader("Authorization") int token,
            @RequestBody String userUUID
    ) {

        List<AvailableConcertDateResponse> result = List.of(
                new AvailableConcertDateResponse(concertCode, "아이유 블루밍 콘서트",
                        LocalDateTime.of(2024, 4, 20, 15, 0, 0)),
                new AvailableConcertDateResponse(concertCode, "아이유 블루밍 콘서트",
                        LocalDateTime.of(2024, 4, 21, 15, 0, 0))
        );

        return ResponseEntity.ok(ConcertApiResponse.of(result));
    }

    @GetMapping("/{concertCode}/seats")
    public ResponseEntity<ConcertApiResponse<?>> findAvailableConcertSeats(
            @PathVariable("concertCode") String concertCode,
            @RequestHeader("Authorization") int token,
            @RequestBody FindConcertSeatsRequest requestDto
    ) {

        LocalDateTime availableConcertDate = LocalDateTime.of(2024, 4, 20, 15, 0, 0);

        List<AvailableConcertSeatResponse> result = List.of(
                new AvailableConcertSeatResponse(concertCode, availableConcertDate, 5, TicketingStatus.NONE),
                new AvailableConcertSeatResponse(concertCode, availableConcertDate, 10, TicketingStatus.NONE),
                new AvailableConcertSeatResponse(concertCode, availableConcertDate, 22, TicketingStatus.NONE),
                new AvailableConcertSeatResponse(concertCode, availableConcertDate, 41, TicketingStatus.NONE),
                new AvailableConcertSeatResponse(concertCode, availableConcertDate, 48, TicketingStatus.NONE)
        );

        return ResponseEntity.ok(ConcertApiResponse.of(result));
    }

    @PatchMapping("/{concertCode}")
    public ResponseEntity<ConcertApiResponse<?>> reserveSeat(
            @PathVariable("concertCode") String concertCode,
            @RequestHeader("Authorization") int token,
            @RequestBody ReservationRequest requestDto
    ) {

        String concertDate = LocalDateTime.of(2024, 4, 20, 15, 0, 0).format(formatter);
        String assignedAt = LocalDateTime.now().plusMinutes(5).format(formatter);
        ReservationResponse result = new ReservationResponse(concertCode, concertDate, 25, assignedAt);

        return ResponseEntity.ok(ConcertApiResponse.of(result));
    }
}
