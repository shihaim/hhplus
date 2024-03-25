package io.hhplus.step2.repository.stub;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.domain.LectureReservation;
import io.hhplus.step2.lecture.repository.LectureReservationCoreRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LectureReservationCoreRepositoryStub implements LectureReservationCoreRepository {

    private final Map<Long, Lecture> lectureDatabase = new HashMap<>();
    private final Map<Long, LectureReservation> lectureReservationDatabase = new HashMap<>();

    @Override
    public Optional<Lecture> findLectureById(Long lectureId) {
        return Optional.ofNullable(lectureDatabase.get(lectureId));
    }

    @Override
    public Optional<LectureReservation> findReservedLectureByUserId(Long userId) {
        return Optional.ofNullable(lectureReservationDatabase.get(userId));
    }

    public Long saveLecture(Lecture lecture) {
        lectureDatabase.put(lecture.getId(), lecture);
        return lecture.getId();
    }

    @Override
    public Long saveLectureReservation(LectureReservation lectureReservation) {
        Long lectureReservationId = lectureReservation.getId();
        Long lectureId = lectureReservation.getLecture().getId();
        Long userId = lectureReservation.getUserId();

        lectureDatabase.get(lectureId).getLectureReservations().add(lectureReservation);
        lectureReservationDatabase.put(userId, lectureReservation);

        return lectureReservationId;
    }
}
