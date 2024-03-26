package io.hhplus.step2.lecture.service;

import io.hhplus.step2.lecture.service.dto.FindLectureDto;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 특강 목록 조회
     * @param searchFromDate
     * @param searchToDate
     * @return
     */
    List<FindLectureDto> findLectureList(final LocalDateTime searchFromDate, final LocalDateTime searchToDate);
}
