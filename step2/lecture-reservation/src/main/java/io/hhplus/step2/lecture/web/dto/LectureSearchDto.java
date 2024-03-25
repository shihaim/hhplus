package io.hhplus.step2.lecture.web.dto;

import java.time.LocalDateTime;

public record LectureSearchDto(
        LocalDateTime searchFromDate,
        LocalDateTime searchToDate
) {
}
