package com.example.ticketing.domain.user.entity;

import com.example.ticketing.domain.token.entity.QueueToken;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_uuid")
    private String userUUID;

    @Column
    private int balance;

    @OneToOne(mappedBy = "user")
    private QueueToken queueToken;

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
