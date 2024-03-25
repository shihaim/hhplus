package io.hhplus.step2.lecture.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LectureReservationErrorResult {
    LECTURE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 특강입니다!"),
    UNABLE_TO_RESERVE_LECTURE(HttpStatus.FORBIDDEN, "해당 날짜에 신청할 수 없는 특강입니다!"),
    ALREADY_RESERVED_LECTURE(HttpStatus.FORBIDDEN, "이미 특강을 신청했습니다!"),
    CLOESED_LECTURE_RESERVATION(HttpStatus.FORBIDDEN, "특강 신청이 마감되었습니다!"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter type"),;

    private final HttpStatus httpStatus;
    private final String message;
}
