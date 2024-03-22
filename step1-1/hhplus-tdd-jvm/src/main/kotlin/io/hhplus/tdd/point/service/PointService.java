package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;

import java.util.List;

public interface PointService {

    UserPoint point(final Long userId);

    UserPoint charge(final Long userId, final Long point);

    UserPoint use(final Long userId, final Long usePoint);

    List<PointHistory> history(final Long userId);
}
