package io.hhplus.step2.lecture;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.repository.component.LectureRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InitData {

    @Autowired
    private LectureRepository lectureRepository;

    @PostConstruct
    void initData() {
        Lecture lecture = Lecture.builder()
                .lectureName("항해플러스 특강")
                .quantity(2)
                .openDate(LocalDateTime.now())
                .build();
        lectureRepository.save(lecture);
    }
}
