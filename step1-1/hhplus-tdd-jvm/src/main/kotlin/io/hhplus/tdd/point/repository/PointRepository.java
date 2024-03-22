package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;

import java.util.List;

public interface PointRepository {

    UserPoint selectById(final Long id);

    UserPoint insertOrUpdate(final Long id, final Long amount);

    PointHistory insert(final Long id, final Long amount, final TransactionType transactionType, final Long updateMillis);

    List<PointHistory> selectAllByUserId(final Long userId);
}
