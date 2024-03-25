package io.hhplus.step2.lecture.service.dto;

import java.time.LocalDateTime;

public record FindLectureDto(
        String lectureName,
        int quantity,
        LocalDateTime openDate
) {
}
