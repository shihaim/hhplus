package io.hhplus.step2.lecture.service.component;

import io.hhplus.step2.lecture.exception.LectureReservationErrorResult;
import io.hhplus.step2.lecture.exception.LectureReservationException;
import io.hhplus.step2.lecture.repository.LectureReservationCoreRepository;
import io.hhplus.step2.lecture.service.dto.FindLectureDto;
import io.hhplus.step2.lecture.service.util.DateFormattingConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<FindLectureDto> findLectureList(LocalDateTime searchFromDate, LocalDateTime searchToDate) {
        if (searchFromDate != null && searchToDate != null) {
            String convertFromDate = DateFormattingConverter.convert(searchFromDate);
            String convertToDate = DateFormattingConverter.convert(searchToDate);
            if (convertToDate.compareTo(convertFromDate) < 0)
                throw new LectureReservationException(LectureReservationErrorResult.INVALID_PARAMETER);
        }

        return repository.findLectureList(searchFromDate, searchToDate)
                .stream()
                .map(l -> new FindLectureDto(l.getLectureName(), l.getQuantity(), l.getOpenDate()))
                .toList();
    }
}
