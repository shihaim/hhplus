package io.hhplus.step2.lecture.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureReservation {

    @Id
    @Column(name = "lecture_reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Builder(access = AccessLevel.PRIVATE)
    public LectureReservation(Long userId, Lecture lecture) {
        this.userId = userId;
        this.lecture = lecture;
    }

    public LectureReservation(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }

    public LectureReservation(Long id, Long userId, Lecture lecture) {
        this.id = id;
        this.userId = userId;
        this.lecture = lecture;
    }

    public static LectureReservation createLectureReservation(Long userId, Lecture lecture) {
        return LectureReservation.builder()
                .userId(userId)
                .lecture(lecture)
                .build();
    }
}
