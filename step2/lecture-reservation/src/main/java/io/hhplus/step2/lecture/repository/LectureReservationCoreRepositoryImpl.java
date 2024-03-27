package io.hhplus.step2.lecture.repository;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.domain.LectureReservation;
import io.hhplus.step2.lecture.repository.component.LectureRepository;
import io.hhplus.step2.lecture.repository.component.LectureReservationRepository;
import io.hhplus.step2.lecture.util.DateFormattingConverter;
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

        return lectureDataFiltering(
                lectureRepository.findAll(),
                DateFormattingConverter.convert(searchFromDate),
                DateFormattingConverter.convert(searchToDate)
        );
    }

    private List<Lecture> lectureDataFiltering(final List<Lecture> allLectures, final String searchFromDate, final String searchToDate) {
        if (searchFromDate != null && searchToDate != null) {
            return allLectures.stream()
                    .filter(l -> DateFormattingConverter.convert(l.getOpenDate()).compareTo(searchFromDate) >= 0)
                    .filter(l -> DateFormattingConverter.convert(l.getOpenDate()).compareTo(searchToDate) <= 0)
                    .toList();
        } else if (searchToDate != null) {
            return allLectures.stream()
                    .filter(l -> DateFormattingConverter.convert(l.getOpenDate()).compareTo(searchToDate) <= 0)
                    .toList();
        } else if (searchFromDate != null) {
            return allLectures.stream()
                    .filter(l -> DateFormattingConverter.convert(l.getOpenDate()).compareTo(searchFromDate) >= 0)
                    .toList();
        } else {
            return allLectures;
        }
    }
}
