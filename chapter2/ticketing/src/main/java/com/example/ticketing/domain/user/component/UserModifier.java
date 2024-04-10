package com.example.ticketing.domain.user.component;

public interface UserModifier {

    /**
     * 잔액 충전
     */
    int chargeBalance(String userUUID, int amount);
}
