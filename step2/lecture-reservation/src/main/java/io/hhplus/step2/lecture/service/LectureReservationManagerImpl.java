package io.hhplus.step2.lecture.service;

import io.hhplus.step2.lecture.service.component.LectureReservationReader;
import io.hhplus.step2.lecture.service.component.LectureReservationWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
