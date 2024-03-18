package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PointControllerTest {

    @InjectMocks
    PointController target;

    @Mock
    PointService pointService;

    UserPointTable userPointTable;
    PointHistoryTable pointHistoryTable;

    MockMvc mockMvc;
    JSONObject jsonObject;

    @BeforeEach
    void setUp() {
        userPointTable = new UserPointTable();
        pointHistoryTable = new PointHistoryTable();
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
        jsonObject = new JSONObject();
    }

    Long userId = 1L;
    Long point = 10000L;

    /**
     * 포인트 조회 API에 대한 테스트 작성
     */
    @Test
    void 포인트조회성공() throws Exception {
        //given
        String url = "/point/1";
        UserPoint userPoint = userPointTable.insertOrUpdate(userId, point);

        doReturn(userPoint).when(pointService).point(userId);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        //then
        resultActions.andExpect(status().isOk());
    }

    /**
     * 포인트 충전 API에 대한 테스트 작성
     */
    @Test
    void 포인트충전성공() throws Exception {
        //given
        String url = "/point/1/charge";
        UserPoint userPoint = userPointTable.insertOrUpdate(userId, point);
        Long amount = 5000L;

        doReturn(userPoint).when(pointService).charge(userId, amount);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(url)
                        .content(String.valueOf(amount))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk());
    }

    /**
     * 포인트 사용 API에 대한 테스트 작성
     */
    @Test
    void 포인트사용성공() throws Exception {
        //given
        String url = "/point/1/use";
        UserPoint userPoint = userPointTable.insertOrUpdate(userId, point);
        Long amount = 5000L;

        doReturn(userPoint).when(pointService).use(userId, amount);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(url)
                        .content(String.valueOf(amount))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk());
    }

    /**
     * 포인트 충전/사용 내역 조회 API에 대한 테스트 작성
     */
    @Test
    void 포인트충전및사용내역조회성공() throws Exception {
        //given
        String url = "/point/1/histories";
        List<PointHistory> pointHistories = Arrays.asList(
                pointHistoryTable.insert(userId, point, TransactionType.CHARGE, System.currentTimeMillis()),
                pointHistoryTable.insert(userId, point, TransactionType.USE, System.currentTimeMillis())
        );

        doReturn(pointHistories).when(pointService).history(userId);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk());
    }
}
