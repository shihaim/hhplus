package io.hhplus.step2.repository;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.domain.LectureReservation;
import io.hhplus.step2.repository.stub.LectureReservationCoreRepositoryStub;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LectureReservationRepositoryTest {

    private final LectureReservationCoreRepositoryStub repository = new LectureReservationCoreRepositoryStub();

    LectureReservation lectureReservation = new LectureReservation(1L, 1L, new Lecture(1L, "항해플러스 특강", 30, LocalDateTime.of(2024, 4, 20, 13, 0)));

    @Test
    void 특강조회실패_빈값() {
        // given
        Long userId = 1L;

        // when
        Optional<Lecture> findLecture = repository.findLectureById(userId);

        // then
        assertThat(findLecture.isEmpty()).isTrue();
    }

    @Test
    void 특강조회성공() {
        // given
        Long lectureId = 1L;

        Lecture lecture = new Lecture(lectureId, "항해플러스 특강", 30, LocalDateTime.of(2024, 4, 20, 13, 0));
        repository.saveLecture(lecture);

        // when
        Optional<Lecture> findLecture = repository.findLectureById(lectureId);

        // then
        assertThat(findLecture.isPresent()).isTrue();
        assertThat(findLecture.get().getId()).isEqualTo(lectureId);
        assertThat(findLecture.get().getLectureName()).isEqualTo("항해플러스 특강");
        assertThat(findLecture.get().getQuantity()).isEqualTo(30);
        assertThat(findLecture.get().getOpenDate()).isEqualTo(LocalDateTime.of(2024,4,20,13,0));
    }

    @Test
    void 특강신청정보조회실패_빈값() {
        // given
        Long userId = 1L;

        // when
        Optional<LectureReservation> findLectureReservation = repository.findReservedLectureByUserId(userId);

        // then
        assertThat(findLectureReservation.isEmpty()).isTrue();
    }

    @Test
    void 특강신청정보조회성공() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;

        Lecture saveLecture = new Lecture(lectureId, "항해플러스 특강", 30, LocalDateTime.of(2024, 4, 20, 13, 0));
        repository.saveLecture(saveLecture);

        LectureReservation lectureReservation = new LectureReservation(userId, saveLecture);
        repository.saveLectureReservation(lectureReservation);

        // when
        Optional<LectureReservation> findLectureReservation = repository.findReservedLectureByUserId(userId);

        // then
        assertThat(findLectureReservation.isPresent()).isTrue();
        assertThat(findLectureReservation.get().getUserId()).isEqualTo(userId);
        assertThat(findLectureReservation.get().getLecture()).isNotNull();
    }

    @Test
    void 특강신청정보저장성공() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;

        Lecture saveLecture = new Lecture(lectureId, "항해플러스 특강", 30, LocalDateTime.of(2024, 4, 20, 13, 0));
        repository.saveLecture(saveLecture);

        // when
        LectureReservation lectureReservation = new LectureReservation(userId, saveLecture);
        Long savedLectureReservation = repository.saveLectureReservation(lectureReservation);

        // then
        assertThat(savedLectureReservation).isEqualTo(lectureReservation.getId());
    }
}
