package io.hhplus.step2.lecture.controller;

import io.hhplus.step2.lecture.domain.Lecture;
import io.hhplus.step2.lecture.repository.component.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lectures")
public class LectureController {

    private final LectureRepository lectureRepository;

    /** 특강 변경을 위한 임의용 */
    @PatchMapping("/{lectureId}")
    @Transactional
    public void updateLecture(@PathVariable Long lectureId,
                              @RequestParam int quantity,
                              @RequestParam LocalDateTime openDate) {
        Lecture lecture = lectureRepository.findById(lectureId).get();
        lecture.update(quantity, openDate);
    }
}
