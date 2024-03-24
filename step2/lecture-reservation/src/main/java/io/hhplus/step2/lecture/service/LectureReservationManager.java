package io.hhplus.step2.lecture.service;

import java.time.LocalDateTime;

public interface LectureReservationManager {

    /**
     * 특강 신청
     * @param userId
     * @param lectureId
     * @param reservationDate
     * @return
     */
    Long lectureReservation(final Long userId, final Long lectureId, final LocalDateTime reservationDate);

    /**
     * 신청한 특강 조회
     * @param userId
     * @return
     */
    String findReservedLecture(final Long userId);
}
