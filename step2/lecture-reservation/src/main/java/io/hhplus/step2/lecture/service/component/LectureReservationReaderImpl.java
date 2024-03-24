package io.hhplus.step2.lecture.service.component;

import io.hhplus.step2.lecture.repository.LectureReservationCoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureReservationReaderImpl implements LectureReservationReader {

    private final LectureReservationCoreRepository repository;

    /**
     * 신청한 특강 조회
     * @param userId
     * @return
     */
    @Override
    public String findReservedLecture(final Long userId) {
        if (repository.findReservedLectureByUserId(userId).isEmpty()) {
            return "실패했음";
        } else {
            return "성공했음";
        }
    }
}
