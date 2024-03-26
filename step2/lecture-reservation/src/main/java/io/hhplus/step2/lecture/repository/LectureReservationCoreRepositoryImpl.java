package io.hhplus.step2.lecture.repository;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.domain.LectureReservation;
import io.hhplus.step2.lecture.repository.component.LectureRepository;
import io.hhplus.step2.lecture.repository.component.LectureReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureReservationCoreRepositoryImpl implements LectureReservationCoreRepository {

    private final LectureRepository lectureRepository;
    private final LectureReservationRepository lectureReservationRepository;

    @Override
    public Optional<Lecture> findLectureById(final Long lectureId) {
        return lectureRepository.findLectureByIdForUpdate(lectureId);
    }

    @Override
    public Optional<LectureReservation> findReservedLectureByUserId(final Long userId) {
        return lectureReservationRepository.findLectureReservationByUserId(userId);
    }

    @Override
    public Long saveLectureReservation(final LectureReservation lectureReservation) {
        return lectureReservationRepository.save(lectureReservation).getId();
    }

    @Override
    public List<Lecture> findLectureList(final LocalDateTime searchFromDate, final LocalDateTime searchToDate) {
        if (searchFromDate != null && searchToDate != null) {
            return lectureRepository.findLecturesByOpenDateBetween(searchFromDate, searchToDate);
        } else if (searchToDate != null) {
            return lectureRepository.findLecturesByOpenDateLessThanEqual(searchToDate);
        } else if (searchFromDate != null) {
            return lectureRepository.findLecturesByOpenDateGreaterThanEqual(searchFromDate);
        } else {
            return lectureRepository.findAll();
        }
    }
}
