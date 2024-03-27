package io.hhplus.step2.repository;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.domain.LectureReservation;
import io.hhplus.step2.repository.stub.FakeLectureReservationCoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LectureReservationRepositoryTest {

    private final FakeLectureReservationCoreRepository repository = new FakeLectureReservationCoreRepository();

    @Test
    void 특강조회실패_빈값() {
        // given
        Long lectureId = -1L;

        // when
        Optional<Lecture> findLecture = repository.findLectureById(lectureId);

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
        Long userId = -1L;

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

    /**
     * [특강 목록 조회]
     *
     * case1: searchFromDate과 searchToDate가 NotNull
     * case2: searchToDate만 NotNull
     * case3: searchFromDate만 NotNull
     * case4: 둘 다 Null
     */
    @Test
    @DisplayName("특강목록조회 - searchFromDate/searchToDate가 Not Null")
    void case1() {
        // given
        // 2024년 4월 21일 부터 25일까지 특강이 한 개씩 존재한다고 가정
        for (int i = 1; i <= 5; i++) {
            Lecture lecture = new Lecture((long) i, "항플 특강" + i, 5, LocalDateTime.of(2024, 4, 20 + i, 13, 0));
            repository.saveLecture(lecture);
        }

        // 23일부터 24일까지
        LocalDateTime searchFromDate = LocalDateTime.of(2024, 4, 23, 0, 0, 0);
        LocalDateTime searchToDate = LocalDateTime.of(2024, 4, 24, 23, 59, 59);

        // when
        List<Lecture> lectureList = repository.findLectureList(searchFromDate, searchToDate);

        // then
        //총 2개가 나와야함. -> 23, 24
        assertThat(lectureList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("특강목록조회 - searchToDate만 Not Null")
    void case2() {
        // given
        // 2024년 4월 21일 부터 25일까지 특강이 한 개씩 존재한다고 가정
        for (int i = 1; i <= 5; i++) {
            Lecture lecture = new Lecture((long) i, "항플 특강" + i, 5, LocalDateTime.of(2024, 4, 20 + i, 13, 0, 0));
            repository.saveLecture(lecture);
        }

        // 23일까지만
        LocalDateTime searchToDate = LocalDateTime.of(2024, 4, 23, 13, 0, 0);

        // when
        List<Lecture> lectureList = repository.findLectureList(null, searchToDate);

        // then
        //총 3개가 나와야함. -> 21, 22, 23
        assertThat(lectureList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("특강목록조회 - searchFromDate만 Not Null")
    void case3() {
        // given
        // 2024년 4월 21일 부터 25일까지 특강이 한 개씩 존재한다고 가정
        for (int i = 1; i <= 5; i++) {
            Lecture lecture = new Lecture((long) i, "항플 특강" + i, 5, LocalDateTime.of(2024, 4, 20 + i, 0, 0, 0));
            repository.saveLecture(lecture);
        }

        // 23일부터
        LocalDateTime searchFromDate = LocalDateTime.of(2024, 4, 23, 0, 0, 0);

        // when
        List<Lecture> lectureList = repository.findLectureList(searchFromDate, null);

        // then
        //총 3개가 나와야함. -> 23, 24, 25
        assertThat(lectureList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("특강목록조회 - 둘 다 Null")
    void case4() {
        // given
        // 2024년 4월 21일 부터 25일까지 특강이 한 개씩 존재한다고 가정
        for (int i = 1; i <= 5; i++) {
            Lecture lecture = new Lecture((long) i, "항플 특강" + i, 5, LocalDateTime.of(2024, 4, 20 + i, 13, 0,0));
            repository.saveLecture(lecture);
        }

        // when
        List<Lecture> lectureList = repository.findLectureList(null, null);

        // then
        //총 5개가 나와야함. -> 21일부터 25일 전부 조회
        assertThat(lectureList.size()).isEqualTo(5);
    }
}
