package io.hhplus.step2.lecture.service.component;

public interface LectureReservationReader {

    /**
     * 신청한 특강 조회
     * @param userId
     * @return
     */
    String findReservedLecture(final Long userId);
}
