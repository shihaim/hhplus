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

//    @PostConstruct
    void initData() {
        for (int i = 0; i < 5; i++) {
            Lecture lecture = Lecture.builder()
                    .lectureName("항해플러스 특강" + (i+1))
                    .quantity(30 + i)
                    .openDate(LocalDateTime.of(2024, 4 + i, 20, 13, 0, 0))
                    .build();
            lectureRepository.save(lecture);
        }
    }
}
