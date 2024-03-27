package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.point.repository.stub.StubPointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PointServiceTest {

    private StubPointRepository repositoryStub = new StubPointRepository();
    private PointService sut = new PointServiceImpl(repositoryStub);

    /**
     * case1: 포인트 조회 성공 (pointRepository에서 조회시 null인 경우 새로운 객체를 생성하므로 id 조회 실패에 대한 실패 테스트는 작성 X)
     * case2: 포인트 충전시 충전할 포인트가 음수인 경우 Error Throw
     * case3: 포인트 충전 성공
     * case4: 포인트 사용 실패 사용할 포인트가 음수인 경우 Error Throw
     * case5: 포인트 사용 실패 현재 가지고 있는 포인트보다 크면 Error Thhrow
     * case6: 포인트 사용 성공
     * case7: 포인트 내역 조회 성공 2건이 존재
     */

    @Test
    @DisplayName("포인트 조회 성공")
    void case1() {
        //given
        Long userId = 1L;
        UserPoint findUserPoint = repositoryStub.findUserPointById(userId);

        //when
        UserPoint result = sut.point(userId);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(findUserPoint.getId());
        assertThat(result.getPoint()).isEqualTo(10000L);
    }

    @Test
    @DisplayName("포인트 충전 실패 - 충전할 포인트가 음수")
    void case2() {
        //given
        Long userId = 1L;
        Long chargePoint = -1L;

        //when
        IllegalArgumentException e = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sut.charge(userId, chargePoint)
        );

        //then
        assertThat(e.getMessage()).isEqualTo("충전할 포인트가 음수일 수 없습니다.");
    }

    @Test
    @DisplayName("포인트 충전 성공")
    void case3() {
        //given
        Long userId = 1L;
        Long chargePoint = 5000L;

        //when
        UserPoint result = sut.charge(userId, chargePoint);

        //then
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getPoint()).isEqualTo(15000L);
    }

    @Test
    @DisplayName("포인트 사용 실패 - 사용할 포인트가 음수")
    void case4() {
        //given
        Long userId = 1L;
        Long usePoint = -1L;

        //when
        IllegalArgumentException e = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sut.use(userId, usePoint)
        );

        //then
        assertThat(e.getMessage()).isEqualTo("사용할 포인트가 음수일 수 없습니다.");
    }

    @Test
    @DisplayName("포인트 사용 실패 - 현재 가지고 있는 포인트보다 큼")
    void case5() {
        //given
        Long userId = 1L;
        Long usePoint = 10001L;

        //when
        IllegalStateException e = Assertions.assertThrows(
                IllegalStateException.class,
                () -> sut.use(userId, usePoint)
        );

        //then
        assertThat(e.getMessage()).isEqualTo("존재하는 포인트보다 클 수 없습니다.");
    }

    @Test
    @DisplayName("포인트 사용 성공")
    void case6() {
        //given
        Long userId = 1L;
        Long usePoint = 10000L;

        //when
        UserPoint result = sut.use(userId, usePoint);

        //then
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getPoint()).isEqualTo(15000L);
    }

    @Test
    @DisplayName("포인트 내역 조회 성공 - 2건이 존재")
    void case7() {
        //given
        Long userId = 1L;

        //when
        List<PointHistory> result = sut.history(userId);

        //then
        assertThat(result.size()).isEqualTo(2);
    }
}