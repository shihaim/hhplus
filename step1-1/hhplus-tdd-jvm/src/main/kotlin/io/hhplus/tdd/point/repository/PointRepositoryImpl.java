package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PointRepositoryImpl implements PointRepository {
    private final Logger logger = LoggerFactory.getLogger(PointRepositoryImpl.class);

    private UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;

    public PointRepositoryImpl(UserPointTable userPointTable, PointHistoryTable pointHistoryTable) {
        this.userPointTable = userPointTable;
        this.pointHistoryTable = pointHistoryTable;
    }

    @Override
    public UserPoint findUserPointById(final Long id) {
        logger.info("call UserPoint.selectById method");
        return userPointTable.selectById(id);
    }

    @Override
    public UserPoint insertOrUpdateToUserPoint(final Long id, final Long amount) {
        logger.info("call UserPoint.insertOrUpdate method");
        return userPointTable.insertOrUpdate(id, amount);
    }

    @Override
    public PointHistory insertToPointHistory(final Long id, final Long amount, final TransactionType transactionType, final Long updateMillis) {
        logger.info("call PointHistory.insert method");
        return pointHistoryTable.insert(id, amount, transactionType, updateMillis);
    }

    @Override
    public List<PointHistory> findAllPointHistoryByUserId(final Long userId) {
        logger.info("call PointHistory.selectAllByUserId method");
        return pointHistoryTable.selectAllByUserId(userId);
    }
}
