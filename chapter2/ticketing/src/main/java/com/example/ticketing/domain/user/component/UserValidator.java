package com.example.ticketing.domain.user.component;

import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void isNegativeAmount(int amount) {
        if (amount < 0) throw new IllegalArgumentException("충전하려는 금액이 음수입니다.");
    }

    public void isLessThanPrice(int price, int balance) {
        if (price > balance) {
            throw new IllegalArgumentException("콘서트의 가격보다 잔액이 적습니다.");
        }
    }
}
