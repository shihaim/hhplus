package io.hhplus.step2.lecture.service;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.service.component.LectureReservationReader;
import io.hhplus.step2.lecture.service.component.LectureReservationWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureReservationManagerImpl implements LectureReservationManager {

    private final LectureReservationReader lectureReservationReader;
    private final LectureReservationWriter lectureReservationWriter;

    /**
     * 특강 신청
     * @param userId
     * @param lectureId
     * @param reservationDate
     * @return
     */
    @Override
    public Long lectureReservation(final Long userId, final Long lectureId, final LocalDateTime reservationDate) {
        return lectureReservationWriter.lectureReservation(userId, lectureId, reservationDate);
    }

    /**
     * 신청한 특강 조회
     * @param userId
     * @return
     */
    @Override
    public String findReservedLecture(final Long userId) {
        return lectureReservationReader.findReservedLecture(userId);
    }

    /**
     * 특강 목록 조회
     * @param searchFromDate
     * @param searchToDate
     * @return
     */
    @Override
    public List<Lecture> findLectureList(final LocalDateTime searchFromDate, final LocalDateTime searchToDate) {
        return lectureReservationReader.findLectureList(searchFromDate, searchToDate);
    }
}
