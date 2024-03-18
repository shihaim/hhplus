package io.hhplus.tdd;

import io.hhplus.tdd.point.PointController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TddApplicationTest {

    @Autowired
    PointController pointController;

    @Test
    void 테스트() throws Exception {
        //given
        pointController.point(1L);
        
        //when
        
        //then
    }
}
