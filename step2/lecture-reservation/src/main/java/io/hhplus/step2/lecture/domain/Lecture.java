package io.hhplus.step2.lecture.domain;

import io.hhplus.step2.lecture.exception.LectureReservationErrorResult;
import io.hhplus.step2.lecture.exception.LectureReservationException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id
    @Column(name = "lecture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lecture_name")
    private String lectureName;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "open_date")
    private LocalDateTime openDate;

    @OneToMany(mappedBy = "lecture")
    private List<LectureReservation> lectureReservations = new ArrayList<>();

    @Builder
    public Lecture(String lectureName, int quantity, LocalDateTime openDate) {
        this.lectureName = lectureName;
        this.quantity = quantity;
        this.openDate = openDate;
    }

    public Lecture(Long id, String lectureName, int quantity, LocalDateTime openDate) {
        this.id = id;
        this.lectureName = lectureName;
        this.quantity = quantity;
        this.openDate = openDate;
    }

    public Lecture(Long id, String lectureName, int quantity, LocalDateTime openDate, List<LectureReservation> lectureReservations) {
        this.id = id;
        this.lectureName = lectureName;
        this.quantity = quantity;
        this.openDate = openDate;
        this.lectureReservations = lectureReservations;
    }

    public void reduceQuantity() {
        if (quantity - 1 < 0) throw new LectureReservationException(LectureReservationErrorResult.CLOESED_LECTURE_RESERVATION);
        quantity -= 1;
    }

    /* 개수 증가를 위한 임시용 */
    public void update(int quantity, LocalDateTime openDate) {
        this.quantity = quantity;
        this.openDate = openDate;
    }
}
