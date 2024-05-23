package com.example.ticketing.api.concert.controller;

import com.example.ticketing.api.concert.dto.*;
import com.example.ticketing.api.concert.usecase.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts")
public class ConcertTicketingController {

    private final IssueQueueTokenUseCase issueQueueTokenUseCase;
    private final IssueQueueTokenUseCaseV2 issueQueueTokenUseCaseV2;
    private final QueuePollingUseCase queuePollingUseCase;
    private final QueuePollingUseCaseV2 queuePollingUseCaseV2;
    private final GetAvailableDateUseCase getAvailableDateUseCase;
    private final GetAvailableSeatsUseCase getAvailableSeatsUseCase;
    private final ReserveSeatUseCase reserveSeatUseCase;

    @GetMapping("/{concertCode}/token")
    public ResponseEntity<ConcertApiResponse<?>> queuePolling(
            @PathVariable("concertCode") String concertCode,
            @RequestHeader("Authorization") int token,
            @RequestBody String userUUID
    ) {
        QueuePollingResponse response = queuePollingUseCaseV2.execute(concertCode, userUUID, token);

        return ResponseEntity.ok(ConcertApiResponse.of(response));
    }

    @PostMapping("/{concertCode}/token")
    public ResponseEntity<ConcertApiResponse<?>> issueToken(
            @PathVariable("concertCode") String concertCode,
            @RequestBody String userUUID
    ) {
//        IssuedTokenResponse response = issueQueueTokenUseCase.execute(concertCode, userUUID);
        IssuedTokenResponse response = issueQueueTokenUseCaseV2.execute(concertCode, userUUID);

        return ResponseEntity.ok(ConcertApiResponse.of(response));
    }

    @GetMapping("/{concertCode}")
    public ResponseEntity<ConcertApiResponse<?>> findAvailableConcertDates(
            @PathVariable("concertCode") String concertCode,
            @RequestHeader("Authorization") int token,
            @RequestBody String userUUID
    ) {
        List<AvailableConcertDateResponse> response = getAvailableDateUseCase.execute(concertCode);

        return ResponseEntity.ok(ConcertApiResponse.of(response));
    }

    @GetMapping("/{concertCode}/seats")
    public ResponseEntity<ConcertApiResponse<?>> findAvailableConcertSeats(
            @PathVariable("concertCode") String concertCode,
            @RequestHeader("Authorization") int token,
            @RequestBody FindConcertSeatsRequest requestDto
    ) {
        List<AvailableConcertSeatResponse> response = getAvailableSeatsUseCase.execute(concertCode, requestDto.concertDate());

        return ResponseEntity.ok(ConcertApiResponse.of(response));
    }

    @PatchMapping("/{concertCode}")
    public ResponseEntity<ConcertApiResponse<?>> reserveSeat(
            @PathVariable("concertCode") String concertCode,
            @RequestHeader("Authorization") int token,
            @RequestBody ReservationRequest requestDto
    ) {
        ReservationResponse response = reserveSeatUseCase.execute(requestDto.userUUID(), token, concertCode, requestDto.concertDate(), requestDto.seatNumber());

        return ResponseEntity.ok(ConcertApiResponse.of(response));
    }
}
