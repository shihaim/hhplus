package com.example.ticketing.api.user.usecase;

import com.example.ticketing.domain.user.component.UserReader;
import com.example.ticketing.domain.user.component.UserValidator;
import com.example.ticketing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChargeBalanceUseCase {

    private final UserReader userReader;
    private final UserValidator userValidator;

    public int execute(String userUUID, int amount) {
        // 1. 충전 금액이 음수인지 확인
        userValidator.isNegativeAmount(amount);
        // 2. 유저 존재 여부 확인후 잔액 충전
        User user = userReader.findUser(userUUID);
        return user.chargeBalance(amount);
    }
}
