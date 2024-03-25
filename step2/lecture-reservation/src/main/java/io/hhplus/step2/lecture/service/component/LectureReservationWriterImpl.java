package io.hhplus.step2.lecture.service.component;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.domain.LectureReservation;
import io.hhplus.step2.lecture.exception.LectureReservationErrorResult;
import io.hhplus.step2.lecture.exception.LectureReservationException;
import io.hhplus.step2.lecture.repository.LectureReservationCoreRepository;
import io.hhplus.step2.lecture.service.util.DateFormattingConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Transactional
@RequiredArgsConstructor
public class LectureReservationWriterImpl implements LectureReservationWriter {

    private final LectureReservationCoreRepository repository;

    /**
     * 특강 신청
     * @param userId
     * @param lectureId
     * @param reservationDate
     * @return
     */
    @Override
    public Long lectureReservation(final Long userId, final Long lectureId, final LocalDateTime reservationDate) {
        Lecture lecture = repository.findLectureById(lectureId)
                .orElseThrow(() -> new LectureReservationException(LectureReservationErrorResult.LECTURE_NOT_FOUND));

        // 0보다 큰 경우
        // (openDate { 2024-04-20 13:00:00 }) compareTo (reservationDate { 2024-04-20 12:59:59 }) = 1
        // 0보다 작거나 같은 경우
        // (openDate { 2024-04-20 13:00:00 }) compareTo (reservationDate { 2024-04-20 13:59:59 }) = 0
        // (openDate { 2024-04-20 13:00:00 }) compareTo (reservationDate { 2024-04-20 14:00:00 }) = -1
        String convertOpenDate = DateFormattingConverter.convert(lecture.getOpenDate());
        String convertReservationDate = DateFormattingConverter.convert(reservationDate);
        if (convertOpenDate.compareTo(convertReservationDate) > 0)
            throw new LectureReservationException(LectureReservationErrorResult.UNABLE_TO_RESERVE_LECTURE);

        // 특강 수량 minus
        lecture.reduceQuantity();

        final long count = lecture.getLectureReservations().stream()
                .filter(reservedLecture -> userId.equals(reservedLecture.getUserId()))
                .count();

        if (count != 0L)
            throw new LectureReservationException(LectureReservationErrorResult.ALREADY_RESERVED_LECTURE);

        // 특강 신청 목록 Save
        LectureReservation lectureReservation = LectureReservation.builder()
                .userId(userId)
                .lecture(lecture)
                .build();

        return repository.saveLectureReservation(lectureReservation);
    }
}
