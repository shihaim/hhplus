package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.point.repository.stub.StubPointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PointRepositoryTest {

    private PointRepository repository = new StubPointRepository();

    /**
     * case1: UserPoint 조회
     * case2: UserPoint Insert&Update
     * case3: PointHistory Insert
     * case4: 포인트 사용/충전 내역 조회
     */
    @Test
    @DisplayName("UserPoint 조회 성공")
    void case1() throws Exception {
        //given
        Long userId = 1L;
        Long point = 10000L;

        //when
        UserPoint result = repository.findUserPointById(userId);

        //then
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getPoint()).isEqualTo(point);
    }

    @Test
    @DisplayName("UserPoint Insert&Update 성공")
    void case2() throws Exception {
        //given
        Long userId = 1L;
        Long amount = 7000L;

        //when
        UserPoint result = repository.insertOrUpdateToUserPoint(userId, amount);

        //then
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getPoint()).isEqualTo(amount);
    }

    @Test
    @DisplayName("PointHistory Insert 성공")
    void case3() throws Exception {
        //given
        Long historyId = -1L;
        Long userId = 1L;
        Long amount = 3000L;
        TransactionType type = TransactionType.CHARGE;

        //when
        PointHistory result = repository.insertToPointHistory(historyId, amount, type, System.currentTimeMillis());

        //then
        assertThat(result.getId()).isEqualTo(historyId);
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getAmount()).isEqualTo(amount);
        assertThat(result.getType()).isEqualTo(type);
    }

    @Test
    @DisplayName("포인트 사용/충전 내역 조회 성공")
    void case4() throws Exception {
        //given
        Long userId = 1L;

        List<PointHistory> pointHistories = List.of(
                new PointHistory(-1L, 1L, TransactionType.CHARGE, 3000L, System.currentTimeMillis()),
                new PointHistory(-2L, 1L, TransactionType.USE, 2000L, System.currentTimeMillis())
        );

        //when
        List<PointHistory> result = repository.findAllPointHistoryByUserId(userId);

        //then
        assertThat(result.size()).isEqualTo(pointHistories.size());
        assertThat(result.get(0).getAmount()).isEqualTo(pointHistories.get(0).getAmount());
        assertThat(result.get(0).getType()).isEqualTo(pointHistories.get(0).getType());
        assertThat(result.get(1).getAmount()).isEqualTo(pointHistories.get(1).getAmount());
        assertThat(result.get(1).getType()).isEqualTo(pointHistories.get(1).getType());
    }
}
