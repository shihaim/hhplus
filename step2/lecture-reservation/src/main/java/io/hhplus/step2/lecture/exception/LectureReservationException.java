package io.hhplus.step2.lecture.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LectureReservationException extends RuntimeException {

    private final LectureReservationErrorResult errorResult;
}
