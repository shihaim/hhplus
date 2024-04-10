package com.example.ticketing.domain.user.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private String userUUID;
    private int balance;

    public int chargeBalance(int amount) {
        return balance += amount;
    }
}
