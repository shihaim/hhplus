package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PointServiceTest {

    PointService pointService;
    UserPointTable userPointTable;
    PointHistoryTable pointHistoryTable;

    Long userId = 1L;
    Long point = 10000L;

    /**
     * 테스트 코드 내 자주 사용하는 setUp용 @BeforEach를 통하여 진행
     */
    @BeforeEach
    void setUp() {
        userPointTable = new UserPointTable();
        pointHistoryTable = new PointHistoryTable();
        pointService = new PointService(userPointTable, pointHistoryTable);
        userPointTable.insertOrUpdate(userId, point);
    }

    /**
     * UserPointTable에서 조회시 null인 경우 새로운 객체를 생성하므로 id 조회 실패에 대한 실패 테스트는 작성 X
     */
    @Test
    void 포인트조회성공() throws Exception {
        //given

        //when
        UserPoint findUserPoint = pointService.point(userId);

        //then
        assertThat(findUserPoint).isNotNull();
        assertThat(findUserPoint.getId()).isEqualTo(userId);
        assertThat(findUserPoint.getPoint()).isEqualTo(point);
        assertThat(findUserPoint.getUpdateMillis()).isNotNull();
    }

    /**
     * 충전할 포인트가 음수이면 안되므로 실패 테스트 작성
     */
    @Test
    void 포인트충전실패_충전할_포인트가_음수() throws Exception {
        //given
        Long chargePoint = -1L;

        //when

        //then
        assertThatThrownBy(() -> pointService.charge(userId, chargePoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("충전할 포인트가 음수일 수 없습니다.");
    }

    /**
     * 포인트 충전 성공 테스트 작성
     */
    @Test
    void 포인트충전성공() throws Exception {
        //given
        Long chargePoint = 5000L;

        //when
        Long totalPoint = pointService.charge(userId, chargePoint).getPoint();

        //then
        assertThat(totalPoint).isEqualTo(point + chargePoint);
    }

    /**
     * 사용할 포인트의 값이 음수이면 안되므로 실패 테스트 작성
     */
    @Test
    void 포인트사용실패_사용할포인트가_음수() throws Exception {
        //given
        Long usePoint = -1L;

        //when

        //then
        assertThatThrownBy(() -> pointService.use(userId, usePoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("사용할 포인트가 음수일 수 없습니다.");
    }

    /**
     * 현재 가지고 있는 포인트보다 사용할 포인트의 값 더 크면 안되므로 실패 테스트 작성
     */
    @Test
    void 포인트사용실패_사용할포인트가_가지고있는_포인트보다_큼() throws Exception {
        //given

        //when
        Long usePoint = 10001L;

        //then
        assertThatThrownBy(() -> pointService.use(userId, usePoint))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("존재하는 포인트보다 클 수 없습니다.");
    }

    /**
     * 포인트 사용 성공 테스트 작성
     */
    @Test
    void 포인트사용성공() throws Exception {
        //given

        //when
        Long usePoint = 10000L;
        Long restPoint = pointService.use(userId, usePoint).getPoint();

        //then
        assertThat(restPoint).isEqualTo(point - usePoint);

    }

    /**
     * 포인트 내역 조회시 충전/사용한 내역이 없는 빈 List 테스트 작성
     */
    @Test
    void 포인트내역조회성공_충전및사용_내역이_없음() throws Exception {
        //given

        //when
        List<PointHistory> findPointHistory = pointService.history(userId);

        //then
        assertThat(findPointHistory.size()).isEqualTo(0);
    }

    /**
     * 포인트 내역 조회시 충전한 내역의 테스트 작성
     */
    @Test
    void 포인트내역조회성공_충전_내역() throws Exception {
        //given
        Long chargePoint = 5000L;
        pointService.charge(userId, chargePoint);

        //when
        List<PointHistory> findPointHistory = pointService.history(userId);

        //then
        assertThat(findPointHistory.get(0).getUserId()).isEqualTo(userId);
        assertThat(findPointHistory.get(0).getAmount()).isEqualTo(chargePoint);
        assertThat(findPointHistory.get(0).getType()).isEqualTo(TransactionType.CHARGE);
    }

    /**
     * 포인트 내역 조회시 사용한 내역의 테스트 작성
     */
    @Test
    void 포인트내역조회성공_사용_내역() throws Exception {
        //given
        Long usePoint = 5000L;
        pointService.use(userId, usePoint);

        //when
        List<PointHistory> findPointHistory = pointService.history(userId);

        //then
        assertThat(findPointHistory.get(0).getUserId()).isEqualTo(userId);
        assertThat(findPointHistory.get(0).getAmount()).isEqualTo(usePoint);
        assertThat(findPointHistory.get(0).getType()).isEqualTo(TransactionType.USE);
    }

    /**
     * 포인트 내역 조회시 다건 내역의 테스트 작성
     */
    @Test
    void 포인트내역조회성공_다건_내역() throws Exception {
        //given
        Long chargePoint = 5000L;
        pointService.charge(userId, chargePoint);

        Long usePoint = 5000L;
        pointService.use(userId, usePoint);

        //when
        List<PointHistory> findPointHistory = pointService.history(userId);

        //then
        assertThat(findPointHistory.size()).isEqualTo(2);
    }
}