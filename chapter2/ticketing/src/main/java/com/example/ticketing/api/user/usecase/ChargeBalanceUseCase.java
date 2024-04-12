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
        userValidator.isNegativeAmount(amount);
        User user = userReader.findUser(userUUID);
        return user.chargeBalance(amount);
    }
}
