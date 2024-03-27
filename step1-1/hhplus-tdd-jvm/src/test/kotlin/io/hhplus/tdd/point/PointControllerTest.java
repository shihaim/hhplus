package io.hhplus.tdd.point;

import io.hhplus.tdd.point.service.stub.StubPointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PointControllerTest {

    @InjectMocks
    PointController target;

    @Mock
    StubPointService serviceStub;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    /**
     * case1: 포인트 조회 API 성공
     * case2: 포인트 충전 API 성공
     * case3: 포인트 사용 API 성공
     * case4: 포인트 충전/사용 내역 조회 API 성공
     */

    @Test
    @DisplayName("포인트 조회 API 성공")
    void case1() throws Exception {
        //given
        String url = "/point/1";
        UserPoint userPoint = new UserPoint(1L, 10000L, System.currentTimeMillis());

        doReturn(userPoint).when(serviceStub).point(1L);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("포인트 충전 API 성공")
    void case2() throws Exception {
        //given
        String url = "/point/1/charge";
        Long amount = 5000L;
        UserPoint userPoint = new UserPoint(1L, 15000L, System.currentTimeMillis());

        doReturn(userPoint).when(serviceStub).charge(1L, amount);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(url)
                        .content(String.valueOf(amount))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("포인트 사용 API 성공")
    void case3() throws Exception {
        //given
        String url = "/point/1/use";
        Long amount = 5000L;
        UserPoint userPoint = new UserPoint(1L, 15000L, System.currentTimeMillis());

        doReturn(userPoint).when(serviceStub).use(1L, amount);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(url)
                        .content(String.valueOf(amount))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("포인트 충전/사용 내역 조회 API 성공")
    void case4() throws Exception {
        //given
        String url = "/point/1/histories";
        List<PointHistory> pointHistories = List.of(
                new PointHistory(-1L, 1L, TransactionType.CHARGE, 3000L, System.currentTimeMillis()),
                new PointHistory(-2L, 1L, TransactionType.USE, 2000L, System.currentTimeMillis())
        );

        doReturn(pointHistories).when(serviceStub).history(1L);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk());
    }
}
