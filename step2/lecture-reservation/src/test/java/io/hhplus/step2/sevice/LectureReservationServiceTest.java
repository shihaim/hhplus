package io.hhplus.step2.sevice;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.domain.LectureReservation;
import io.hhplus.step2.lecture.exception.LectureReservationErrorResult;
import io.hhplus.step2.lecture.exception.LectureReservationException;
import io.hhplus.step2.lecture.service.component.LectureReservationReader;
import io.hhplus.step2.lecture.service.component.LectureReservationReaderImpl;
import io.hhplus.step2.lecture.service.component.LectureReservationWriter;
import io.hhplus.step2.lecture.service.component.LectureReservationWriterImpl;
import io.hhplus.step2.repository.stub.LectureReservationCoreRepositoryStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LectureReservationServiceTest {

    private final LectureReservationCoreRepositoryStub repository = new LectureReservationCoreRepositoryStub();
    private final LectureReservationReader readerSut = new LectureReservationReaderImpl(repository);
    private final LectureReservationWriter writerSut = new LectureReservationWriterImpl(repository);

    /**
     * [특강 신청]
     * case1: 존재하지 않는 특강에 대해 Error Throw
     * case2: 동일한 신청자는 한 번의 수강 신청만 성공할 수 있고 재신청시 Error Throw
     * case3: 특강은 4월 20일 토요일 1시에 열리고, 그 전 시간에 신청시 Error Throw
     * case4: 선착순 30명만 신청 가능하고, 초과되면 요청을 실패시키고 Error Throw
     * case5: 특강 신청 성공
     */
    @Test
    @DisplayName("특강신청실패 - 존재하지 않는 특강")
     void case1() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;

        // when
        LectureReservationException result =
                assertThrows(
                        LectureReservationException.class,
                        () -> writerSut.lectureReservation(userId, lectureId, LocalDateTime.of(2024, 4, 20, 13, 0))
                );

        // then
        assertThat(result.getErrorResult()).isEqualTo(LectureReservationErrorResult.LECTURE_NOT_FOUND);
    }

    @Test
    @DisplayName("특강신청실패 - 한 번만 신청할 수 있음")
    void case2() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;

        // 특강 저장
        Lecture saveLecture = new Lecture(lectureId, "항해플러스 특강", 30, LocalDateTime.of(2024, 4, 20, 13, 0));
        repository.saveLecture(saveLecture);

        // 특강 정보 조회
        Lecture lecture = repository.findLectureById(lectureId).get();

        // 특강 신청 정보 저장
        LectureReservation lectureReservation = new LectureReservation(userId, lectureId, lecture);
        lecture.getLectureReservations().add(lectureReservation);
        repository.saveLectureReservation(lectureReservation);

        LocalDateTime reservationDate = LocalDateTime.of(2024, 4, 20, 13, 0);

        // when
        LectureReservationException result = assertThrows(
                LectureReservationException.class,
                () -> writerSut.lectureReservation(userId, lectureId, reservationDate)
        );

        // then
        assertThat(result.getErrorResult()).isEqualTo(LectureReservationErrorResult.ALREADY_RESERVED_LECTURE);
    }

    @Test
    @DisplayName("특강신청실패 - 4월 20일 토요일 1시에 열림")
    void case3() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;

        // 특강 저장
        Lecture saveLecture = new Lecture(lectureId, "항해플러스 특강", 30, LocalDateTime.of(2024, 4, 20, 13, 0));
        repository.saveLecture(saveLecture);

        // 4월 20일 토요일 12시 59분 59초에 실행
        LocalDateTime reservationDate = LocalDateTime.of(2024, 4, 20, 12, 59, 59);

        // when
        LectureReservationException result = assertThrows(
                LectureReservationException.class,
                () -> writerSut.lectureReservation(userId, lectureId, reservationDate)
        );

        // then
        assertThat(result.getErrorResult()).isEqualTo(LectureReservationErrorResult.UNABLE_TO_RESERVE_LECTURE);
    }

    @Test
    @DisplayName("특강신청실패 - 선착순 30명만 신청 가능")
    void case4() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;

        // 특강 저장
        Lecture saveLecture = new Lecture(lectureId, "항해플러스 특강", 30, LocalDateTime.of(2024, 4, 20, 13, 0));
        repository.saveLecture(saveLecture);

        // 특강 정보 조회 및 특강 신청 마감됐음을 가정
        Lecture findLecture = repository.findLectureById(lectureId).get();
        for (int i = 0; i < 30; i++) {
            findLecture.reduceQuantity();
        }

        // when
        LocalDateTime reservationDate = LocalDateTime.of(2024, 4, 20, 13, 0);
        LectureReservationException result = assertThrows(
                LectureReservationException.class,
                () -> writerSut.lectureReservation(userId, lectureId, reservationDate)
        );

        // then
        assertThat(result.getErrorResult()).isEqualTo(LectureReservationErrorResult.CLOESED_LECTURE_RESERVATION);
    }

    @Test
    @DisplayName("특강신청성공")
    void case5() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;

        // 특강 저장
        Lecture saveLecture = new Lecture(lectureId, "항해플러스 특강", 30, LocalDateTime.of(2024, 4, 20, 13, 0));
        repository.saveLecture(saveLecture);

        // when
        LocalDateTime reservationDate = LocalDateTime.of(2024, 4, 20, 13, 0);
        Long savedLectureReservation = writerSut.lectureReservation(userId, lectureId, reservationDate);

        LectureReservation findLectureReservation = repository.findReservedLectureByUserId(userId).get();
        Lecture findLecture = findLectureReservation.getLecture();

        // then
        assertThat(findLectureReservation.getUserId()).isEqualTo(userId);
        assertThat(findLecture.getId()).isEqualTo(lectureId);
        assertThat(findLecture.getLectureName()).isEqualTo("항해플러스 특강");
        assertThat(findLecture.getQuantity()).isEqualTo(29);
    }


    /**
     * [특강 신청 완료 여부 조회]
     * case6: 툭강 신청에 성공한 사용자는 "성공했음"을 반환
     * case7: 명단에 없는 사용자는 "실패했음"을 반환
     */

    @Test
    @DisplayName("특강신청정보조회실패 - 명단에 없는 사용자")
    void case6() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;

        // 특강 저장
        Lecture saveLecture = new Lecture(lectureId, "항해플러스 특강", 30, LocalDateTime.of(2024, 4, 20, 13, 0));
        repository.saveLecture(saveLecture);

        //특강 신청 정보 저장
        LectureReservation saveLectureReservation = new LectureReservation(userId, saveLecture);
        repository.saveLectureReservation(saveLectureReservation);

        // when
        String result = readerSut.findReservedLecture(2L);

        // then
        assertThat(result).isEqualTo("실패했음");
    }

    @Test
    @DisplayName("특강신청정보조회성공")
    void case7() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;

        // 특강 저장
        Lecture saveLecture = new Lecture(lectureId, "항해플러스 특강", 30, LocalDateTime.of(2024, 4, 20, 13, 0));
        repository.saveLecture(saveLecture);

        //특강 신청 정보 저장
        LectureReservation saveLectureReservation = new LectureReservation(userId, saveLecture);
        repository.saveLectureReservation(saveLectureReservation);

        // when
        String result = readerSut.findReservedLecture(1L);

        // then
        assertThat(result).isEqualTo("성공했음");
    }

}
