package io.hhplus.step2.lecture.service.component;

import java.time.LocalDateTime;

public interface LectureReservationStore {

    /**
     * 특강 신청
     * @param userId
     * @param lectureId
     * @param reservationDate
     * @return
     */
    Long lectureReservation(final Long userId, final Long lectureId, final LocalDateTime reservationDate);
}
