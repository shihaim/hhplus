package io.hhplus.step2.lecture.web;

import io.hhplus.step2.lecture.common.ReservationResponse;
import io.hhplus.step2.lecture.service.LectureReservationManager;
import io.hhplus.step2.lecture.web.dto.ReservationCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lectures")
public class LectureReservationController {

    private final LectureReservationManager manager;

    @PostMapping("/{userId}")
    public ResponseEntity<ReservationResponse> reservation(
            @PathVariable Long userId,
            @RequestBody @Valid ReservationCreateDto dto
    ) {
        Long lectureReservationId = manager.lectureReservation(userId, dto.lectureId(), dto.reservationDate());
        return ResponseEntity.ok(ReservationResponse.create("신청 완료! 특강 신청 번호 : [%s]".formatted(lectureReservationId), HttpStatus.OK));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ReservationResponse> reservedLecture(
            @PathVariable Long userId
    ) {
        String result = manager.findReservedLecture(userId);
        return ResponseEntity.ok(ReservationResponse.create(result, HttpStatus.OK));
    }
}
