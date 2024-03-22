package io.hhplus.tdd.point;

import io.hhplus.tdd.point.repository.PointRepositoryImpl;
import io.hhplus.tdd.point.service.PointServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @InjectMocks
    private PointServiceImpl pointService;

    @Mock
    PointRepositoryImpl pointRepository;

    /**
     * pointRepository에서 조회시 null인 경우 새로운 객체를 생성하므로 id 조회 실패에 대한 실패 테스트는 작성 X
     */
    @Test
    void 포인트조회성공() {
        //given
        Long userId = 1L;
        Long point = 10000L;
        UserPoint userPoint = new UserPoint(userId, point, System.currentTimeMillis());
        doReturn(userPoint).when(pointRepository).selectById(userId);

        //when
        UserPoint findUserPoint = pointService.point(userId);

        //then
        assertThat(findUserPoint).isNotNull();
        assertThat(findUserPoint.getId()).isEqualTo(userId);
        assertThat(findUserPoint.getPoint()).isEqualTo(point);
    }

    /**
     * 충전할 포인트가 음수이면 안되므로 실패 테스트 작성
     */
    @Test
    void 포인트충전실패_충전할_포인트가_음수() {
        //given
        Long userId = 1L;
        Long chargePoint = -1L;

        //when
        ThrowingCallable throwingCallable = () -> pointService.charge(userId, chargePoint);

        //then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("충전할 포인트가 음수일 수 없습니다.");
    }

    /**
     * 포인트 충전 성공 테스트 작성
     */
    @Test
    void 포인트충전성공() {
        //given
        Long userId = 1L;
        Long point = 10000L;
        long updateMillis = System.currentTimeMillis();
        UserPoint userPoint = new UserPoint(userId, point, updateMillis);
        doReturn(userPoint).when(pointRepository).selectById(userId);

        Long chargePoint = 5000L;
        UserPoint chargingPoint = new UserPoint(userId, userPoint.getPoint() + chargePoint, updateMillis);
        doReturn(chargingPoint).when(pointRepository).insertOrUpdate(userId, userPoint.getPoint() + chargePoint);

        //when
        UserPoint result = pointService.charge(userId, chargePoint);

        //then
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getPoint()).isEqualTo(chargingPoint.getPoint());
        assertThat(result.getUpdateMillis()).isEqualTo(updateMillis);
    }

    /**
     * 포인트 충전 성공 이후 포인트 내역 저장에 대한 테스트 작성
     *  + 포인트 사용 내역인지에 대한 테스트는 진행하지 않았음. 비즈니스 로직상 당연히 불가능한 부분이라고 생각
     *  Q1. 물론 서비스 로직에서 충전을 하면 그 내역을 저장하기 마련인데, Mocking 테스트시 해당 내역의 대한 상태 검증을 해야하는 부분인가?
     *      만약 상태 검증을 해야한다면 gwt 단계에서 pointHistory 이후에는 UserPoint를 반환하기에 어떻게 검증을 할 수 있는가?
     */
    @Test
    void 포인트충전성공_포인트충전내역_저장성공() {
        //given
        Long userId = 1L;
        Long point = 10000L;
        long updateMillis = System.currentTimeMillis();
        UserPoint userPoint = new UserPoint(userId, point, updateMillis);
        doReturn(userPoint).when(pointRepository).selectById(userId);

        Long chargePoint = 5000L;
        UserPoint chargingUserPoint = new UserPoint(userId, point + chargePoint, updateMillis);
        doReturn(chargingUserPoint).when(pointRepository).insertOrUpdate(userId, point + chargePoint);

        TransactionType type = TransactionType.CHARGE;
        PointHistory pointHistory = new PointHistory(-1L, userId, type, chargePoint, chargingUserPoint.getUpdateMillis());
        doReturn(pointHistory).when(pointRepository).insert(userId, chargePoint, type, chargingUserPoint.getUpdateMillis());

        //when
        pointService.charge(userId, chargePoint);

        //then
    }

    /**
     * 사용할 포인트의 값이 음수이면 안되므로 실패 테스트 작성
     */
    @Test
    void 포인트사용실패_사용할포인트가_음수() {
        //given
        Long userId = 1L;
        Long usePoint = -1L;

        //when
        ThrowingCallable throwingCallable = () -> pointService.use(userId, usePoint);

        //then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("사용할 포인트가 음수일 수 없습니다.");
    }

    /**
     * 현재 가지고 있는 포인트보다 사용할 포인트의 값 더 크면 안되므로 실패 테스트 작성
     */
    @Test
    void 포인트사용실패_사용할포인트가_가지고있는_포인트보다_큼() {
        //given
        Long userId = 1L;
        Long point = 10000L;
        long updateMillis = System.currentTimeMillis();
        UserPoint userPoint = new UserPoint(userId, point, updateMillis);
        doReturn(userPoint).when(pointRepository).selectById(userId);

        //when
        Long usePoint = 10001L;
        ThrowingCallable throwingCallable = () -> pointService.use(userId, usePoint);

        //then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("존재하는 포인트보다 클 수 없습니다.");
    }

    /**
     * 포인트 이용 성공 이후 포인트 내역 저장에 대한 테스트 작성
     *  + 포인트 충전 내역인지에 대한 테스트는 진행하지 않았음. 비즈니스 로직상 당연히 불가능한 부분이라고 생각
     *
     *  Q1. 위와 동일
     */
    @Test
    void 포인트사용성공_포인트이용내역_저장성공() {
        //given
        Long userId = 1L;
        Long point = 10000L;
        long updateMillis = System.currentTimeMillis();
        UserPoint userPoint = new UserPoint(userId, point, updateMillis);
        doReturn(userPoint).when(pointRepository).selectById(userId);

        Long usePoint = 10000L;
        UserPoint usingUserPoint = new UserPoint(userId, userPoint.getPoint() - usePoint, updateMillis);
        doReturn(usingUserPoint).when(pointRepository).insertOrUpdate(userId, userPoint.getPoint() - usePoint);

        TransactionType type = TransactionType.USE;
        PointHistory pointHistory = new PointHistory(-1L, userId, type, usePoint, updateMillis);
        doReturn(pointHistory).when(pointRepository).insert(userId, usePoint, type, updateMillis);

        //when
        pointService.use(userId, usePoint);

        //then
    }

    /**
     * 포인트 사용 성공 테스트 작성
     */
    @Test
    void 포인트사용성공() {
        //given
        Long userId = 1L;
        Long point = 10000L;
        long updateMillis = System.currentTimeMillis();
        UserPoint userPoint = new UserPoint(userId, point, updateMillis);
        doReturn(userPoint).when(pointRepository).selectById(userId);


        Long usePoint = 10000L;
        UserPoint usingUserPoint = new UserPoint(userId, userPoint.getPoint() - usePoint, updateMillis);
        doReturn(usingUserPoint).when(pointRepository).insertOrUpdate(userId, userPoint.getPoint() - usePoint);

        TransactionType type = TransactionType.USE;
        PointHistory pointHistory = new PointHistory(-1L, userId, type, usePoint, updateMillis);
        doReturn(pointHistory).when(pointRepository).insert(userId, usePoint, type, updateMillis);

        //when
        UserPoint result = pointService.use(userId, usePoint);

        //then
        assertThat(result.getId()).isEqualTo(usingUserPoint.getId());
        assertThat(result.getPoint()).isEqualTo(usingUserPoint.getPoint());
        assertThat(result.getUpdateMillis()).isEqualTo(usingUserPoint.getUpdateMillis());
    }

    /**
     * 포인트 내역 조회시 충전/사용한 내역이 없는 빈 List 테스트 작성
     */
    @Test
    void 포인트내역조회성공_충전및사용_내역이_없음() {
        //given
        Long userId = 1L;
        doReturn(List.of()).when(pointRepository).selectAllByUserId(userId);

        //when
        List<PointHistory> findPointHistory = pointService.history(userId);

        //then
        assertThat(findPointHistory.size()).isEqualTo(0);
    }

    /**
     * 포인트 내역 조회시 다건 내역의 테스트 작성
     */
    @Test
    void 포인트내역조회성공_다건_내역() {
        //given
        Long userId = 1L;
        doReturn(List.of(
                new PointHistory(1L, 1L, TransactionType.CHARGE, 5000L, System.currentTimeMillis()),
                new PointHistory(2L, 1L, TransactionType.USE, 5000L, System.currentTimeMillis())
        )).when(pointRepository).selectAllByUserId(userId);

        //when
        List<PointHistory> result = pointService.history(userId);

        //then
        assertThat(result.size()).isEqualTo(2);
    }
}