package io.hhplus.tdd.point.repository.stub;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.point.repository.PointRepository;

import java.util.List;

public class StubPointRepository implements PointRepository {
    @Override
    public UserPoint findUserPointById(Long id) {
        return new UserPoint(1L, 10000L, System.currentTimeMillis());
    }

    @Override
    public UserPoint insertOrUpdateToUserPoint(Long id, Long amount) {
        return new UserPoint(1L, 15000L, System.currentTimeMillis());
    }

    @Override
    public PointHistory insertToPointHistory(Long id, Long amount, TransactionType transactionType, Long updateMillis) {
        if (transactionType == TransactionType.CHARGE) {
            return new PointHistory(-1L, 1L, TransactionType.CHARGE, 3000L, System.currentTimeMillis());
        } else {
            return new PointHistory(-1L, 1L, TransactionType.USE, 2000L, System.currentTimeMillis());
        }
    }

    @Override
    public List<PointHistory> findAllPointHistoryByUserId(Long userId) {
        return List.of(
                new PointHistory(-1L, 1L, TransactionType.CHARGE, 3000L, System.currentTimeMillis()),
                new PointHistory(-2L, 1L, TransactionType.USE, 2000L, System.currentTimeMillis())
        );
    }
}
