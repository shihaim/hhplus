package com.example.ticketing.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    private String userUUID;
    @Column
    private int balance;

    @Builder
    public User(String userUUID, int balance) {
        this.userUUID = userUUID;
        this.balance = balance;
    }

    /**
     * 잔액 충전
     */
    public int chargeBalance(int amount) {
        return balance += amount;
    }
}
