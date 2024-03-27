package io.hhplus.step2.lecture.repository.component;

import io.hhplus.step2.lecture.domain.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from Lecture l where l.id = :lectureId")
    Optional<Lecture> findLectureByIdForUpdate(@Param("lectureId") Long lectureId);
}
