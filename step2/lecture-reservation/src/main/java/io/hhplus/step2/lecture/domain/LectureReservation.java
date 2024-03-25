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
    @JoinColumn(name = "lecutre_id")
    private Lecture lecture;

    @Builder
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
}
