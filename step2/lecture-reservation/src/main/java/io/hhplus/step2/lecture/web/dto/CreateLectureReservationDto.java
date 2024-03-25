package io.hhplus.step2.lecture.web.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record CreateLectureReservationDto(
        @NotNull
        @Positive
        Long lectureId,

        @NotNull
        @FutureOrPresent
        LocalDateTime reservationDate
) {

}
