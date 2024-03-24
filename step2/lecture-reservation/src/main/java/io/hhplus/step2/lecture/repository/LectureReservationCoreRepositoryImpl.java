package io.hhplus.step2.lecture.repository;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.domain.LectureReservation;
import io.hhplus.step2.lecture.repository.component.LectureRepository;
import io.hhplus.step2.lecture.repository.component.LectureReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
