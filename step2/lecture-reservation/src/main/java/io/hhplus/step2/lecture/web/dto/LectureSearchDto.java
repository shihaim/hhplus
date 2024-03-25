package io.hhplus.step2.lecture.web.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LectureSearchDto(
        @NotNull @FutureOrPresent
        LocalDateTime openDate
) {
}
