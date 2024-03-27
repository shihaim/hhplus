package io.hhplus.tdd.point.service.stub;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.point.service.PointService;

import java.util.List;

public class StubPointService implements PointService {
    @Override
    public UserPoint point(Long userId) {
        return new UserPoint(1L, 10000L, System.currentTimeMillis());
    }

    @Override
    public UserPoint charge(Long userId, Long point) {
        return new UserPoint(1L, 15000L, System.currentTimeMillis());
    }

    @Override
    public UserPoint use(Long userId, Long usePoint) {
        return new UserPoint(1L, 5000L, System.currentTimeMillis());
    }

    @Override
    public List<PointHistory> history(Long userId) {
        return List.of(
                new PointHistory(-1L, 1L, TransactionType.CHARGE, 3000L, System.currentTimeMillis()),
                new PointHistory(-2L, 1L, TransactionType.USE, 2000L, System.currentTimeMillis())
        );
    }
}
