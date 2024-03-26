package io.hhplus.step2.lecture.repository;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.domain.LectureReservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureReservationCoreRepository {

    Optional<Lecture> findLectureById(final Long lectureId);
    Optional<LectureReservation> findReservedLectureByUserId(final Long userId);
    Long saveLectureReservation(final LectureReservation lectureReservation);
    List<Lecture> findLectureList(final LocalDateTime searchFromDate, final LocalDateTime searchToDate);
}
