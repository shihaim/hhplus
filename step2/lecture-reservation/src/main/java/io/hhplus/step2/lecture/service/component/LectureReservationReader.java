package io.hhplus.step2.lecture.service.component;

import io.hhplus.step2.lecture.domain.Lecture;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureReservationReader {

    /**
     * 신청한 특강 조회
     * @param userId
     * @return
     */
    String findReservedLecture(final Long userId);

    /**
     * 특강 목록 조회
     * @param searchFromDate
     * @param searchToDate
     * @return
     */
    List<Lecture> findLectureList(final LocalDateTime searchFromDate, final LocalDateTime searchToDate);
}
