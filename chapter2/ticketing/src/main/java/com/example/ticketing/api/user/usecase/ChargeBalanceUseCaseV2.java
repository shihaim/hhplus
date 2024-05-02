package com.example.ticketing.api.user.usecase;

import com.example.ticketing.domain.handler.lock.LockHandler;
import com.example.ticketing.domain.handler.transaction.TransactionHandler;
import com.example.ticketing.domain.user.component.UserReader;
import com.example.ticketing.domain.user.component.UserValidator;
import com.example.ticketing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChargeBalanceUseCaseV2 {
    private final static String CHARGE_BALANCE_USE_CASE_LOCK_PREFIX = "CHARGE_BALANCE_";
    private final LockHandler lockHandler;
    private final TransactionHandler transactionHandler;

    private final UserReader userReader;
    private final UserValidator userValidator;

    public int execute(String userUUID, int amount) {
        int result = lockHandler.runOnLock(CHARGE_BALANCE_USE_CASE_LOCK_PREFIX + userUUID, 5L, 3L,
                () -> transactionHandler.runOnWriteTransaction(() -> {
                    // 1. 충전 금액이 음수인지 확인
                    userValidator.isNegativeAmount(amount);
                    // 2. 유저 존재 여부 확인후 잔액 충전
                    User user = userReader.findUser(userUUID);
                    return user.chargeBalance(amount);
                }));

        return result;
    }
}
