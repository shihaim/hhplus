package io.hhplus.step2.lecture.controller.dto;

import java.time.LocalDateTime;

public record LectureSearchDto(
        LocalDateTime searchFromDate,
        LocalDateTime searchToDate
) {
}
