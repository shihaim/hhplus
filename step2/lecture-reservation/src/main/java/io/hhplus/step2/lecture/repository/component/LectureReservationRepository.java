package io.hhplus.step2.lecture.repository.component;

import io.hhplus.step2.lecture.domain.LectureReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LectureReservationRepository extends JpaRepository<LectureReservation, Long> {

    Optional<LectureReservation> findLectureReservationByUserId(@Param("userId") Long userId);
}
