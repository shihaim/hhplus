package io.hhplus.tdd.point.service;

import io.hhplus.tdd.LockHandler;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.point.repository.PointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;

@Service
public class PointServiceImpl implements PointService {
    private final Logger logger = LoggerFactory.getLogger(PointServiceImpl.class);

    private final PointRepository pointRepository;
    private final LockHandler lockHandler;

    public PointServiceImpl(PointRepository pointRepository, LockHandler lockHandler) {
        this.pointRepository = pointRepository;
        this.lockHandler = lockHandler;
    }

    @Override
    public UserPoint point(final Long userId) {
        logger.info("call point method");
        return pointRepository.findUserPointById(userId);
    }

    @Override
    public UserPoint charge(final Long userId, final Long point) {
        logger.info("call charge method");
        return lockHandler.lock(userId, () -> {
            if (point < 0) {
                throw new IllegalArgumentException("충전할 포인트가 음수일 수 없습니다.");
            }

            // 현재 포인트를 조회하여 포인트를 충전
            final UserPoint userPoint = pointRepository.findUserPointById(userId);
            final Long chargePoint = userPoint.getPoint() + point;
            UserPoint result = pointRepository.insertOrUpdateToUserPoint(userId, chargePoint);

            // 충전 내역 저장
            pointRepository.insertToPointHistory(userId, point, TransactionType.CHARGE, result.getUpdateMillis());

            return result;
        });
    }

    @Override
    public UserPoint use(final Long userId, final Long usePoint) {
        logger.info("call use method");
        return lockHandler.lock(userId, () -> {
            if (usePoint < 0) {
                throw new IllegalArgumentException("사용할 포인트가 음수일 수 없습니다.");
            }

            // 현재 포인트를 조회하여 포인트를 사용
            final UserPoint findUserPoint = pointRepository.findUserPointById(userId);
            if (findUserPoint.getPoint() - usePoint < 0) {
                throw new IllegalStateException("존재하는 포인트보다 클 수 없습니다.");
            }
            final Long restPoint = findUserPoint.getPoint() - usePoint;
            UserPoint result = pointRepository.insertOrUpdateToUserPoint(userId, restPoint);

            // 사용 내역 저장
            pointRepository.insertToPointHistory(result.getId(), usePoint, TransactionType.USE, result.getUpdateMillis());

            return result;
        });
    }

    @Override
    public List<PointHistory> history(final Long userId) {
        logger.info("call history method");
        return pointRepository.findAllPointHistoryByUserId(userId);
    }
}
