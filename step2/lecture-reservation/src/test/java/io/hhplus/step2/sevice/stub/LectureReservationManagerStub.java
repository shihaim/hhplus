package io.hhplus.step2.sevice.stub;

import io.hhplus.step2.lecture.service.LectureReservationManager;

import java.time.LocalDateTime;

public class LectureReservationManagerStub implements LectureReservationManager {
    @Override
    public Long lectureReservation(Long userId, Long lectureId, LocalDateTime reservationDate) {
        return 1L;
    }

    @Override
    public String findReservedLecture(Long userId) {
        if (userId == 1L) return "성공했음";
        return "실패했음";
    }
}
