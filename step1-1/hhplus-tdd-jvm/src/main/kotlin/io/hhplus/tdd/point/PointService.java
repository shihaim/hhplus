package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {

    private final Logger logger = LoggerFactory.getLogger(PointService.class);

    private final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;

    public PointService(final UserPointTable userPointTable, final PointHistoryTable pointHistoryTable) {
        this.userPointTable = userPointTable;
        this.pointHistoryTable = pointHistoryTable;
    }

    public UserPoint point(final Long userId) {
        return userPointTable.selectById(userId);
    }

    public UserPoint charge(final Long userId, final Long point) {
        if (point < 0) {
            throw new IllegalArgumentException("충전할 포인트가 음수일 수 없습니다.");
        }

        final UserPoint userPoint = userPointTable.selectById(userId);
        final Long chargePoint = userPoint.getPoint() + point;

        pointHistoryTable.insert(userId, point, TransactionType.CHARGE, System.currentTimeMillis());

        return userPointTable.insertOrUpdate(userId, chargePoint);
    }

    public UserPoint use(final Long userId, final Long usePoint) {
        if (usePoint < 0) {
            throw new IllegalArgumentException("사용할 포인트가 음수일 수 없습니다.");
        }

        final UserPoint findUserPoint = userPointTable.selectById(userId);
        if (findUserPoint.getPoint() - usePoint < 0) {
            throw new IllegalStateException("존재하는 포인트보다 클 수 없습니다.");
        }

        final Long restPoint = findUserPoint.getPoint() - usePoint;

        pointHistoryTable.insert(userId, usePoint, TransactionType.USE, System.currentTimeMillis());

        return userPointTable.insertOrUpdate(userId, restPoint);
    }

    public List<PointHistory> history(final Long userId) {
        return pointHistoryTable.selectAllByUserId(userId);
    }
}
