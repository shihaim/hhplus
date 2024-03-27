package io.hhplus.step2.sevice.stub;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.service.LectureReservationManager;

import java.time.LocalDateTime;
import java.util.List;

public class StubLectureReservationManager implements LectureReservationManager {
    @Override
    public Long lectureReservation(Long userId, Long lectureId, LocalDateTime reservationDate) {
        return 1L;
    }

    @Override
    public String findReservedLecture(Long userId) {
        if (userId == 1L) return "성공했음";
        return "실패했음";
    }

    @Override
    public List<Lecture> findLectureList(LocalDateTime searchFromDate, LocalDateTime searchToDate) {
        return List.of(
                new Lecture("항플 특강1", 10, LocalDateTime.of(2024, 4, 20, 13, 0, 0)),
                new Lecture("항플 특강2", 10, LocalDateTime.of(2024, 4, 21, 13, 0, 0)),
                new Lecture("항플 특강3", 10, LocalDateTime.of(2024, 4, 22, 13, 0, 0))
        );
    }
}
