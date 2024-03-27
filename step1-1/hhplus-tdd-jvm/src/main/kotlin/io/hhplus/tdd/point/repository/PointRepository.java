package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;

import java.util.List;

public interface PointRepository {

    UserPoint findUserPointById(final Long id);

    UserPoint insertOrUpdateToUserPoint(final Long id, final Long amount);

    PointHistory insertToPointHistory(final Long id, final Long amount, final TransactionType transactionType, final Long updateMillis);

    List<PointHistory> findAllPointHistoryByUserId(final Long userId);
}
