package io.hhplus.step2.lecture.web;

import io.hhplus.step2.lecture.common.ReservationResponse;
import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.repository.component.LectureRepository;
import io.hhplus.step2.lecture.service.LectureReservationManager;
import io.hhplus.step2.lecture.web.dto.ReservationCreateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lectures")
public class LectureReservationController {

    private final LectureReservationManager manager;

    /** 특강 신청 */
    @PostMapping("/{userId}")
    public ResponseEntity<ReservationResponse> reservation(
            // Spring 3.2부터 자바 컴파일러에 -parameters 옵션을 넣어줘야 어노테이션 이름 생략 가능.
            // Spring @PathVariable 검증시 어노테이션에 이름이 존재하지 않는다면 Error가 발생하니 주의 필요
            @PathVariable("userId") @Min(1L) Long userId,
            @RequestBody @Valid ReservationCreateDto dto
    ) {
        Long lectureReservationId = manager.lectureReservation(userId, dto.lectureId(), dto.reservationDate());
        return ResponseEntity.ok(ReservationResponse.create("신청 완료! 특강 신청 번호 : [%s]".formatted(lectureReservationId), HttpStatus.OK));
    }

    /** 특강 신청 정보 조회 */
    @GetMapping("/{userId}")
    public ResponseEntity<ReservationResponse> reservedLecture(
            @PathVariable("userId") @Min(1L) Long userId
    ) {
        String result = manager.findReservedLecture(userId);
        return ResponseEntity.ok(ReservationResponse.create(result, HttpStatus.OK));
    }
}
