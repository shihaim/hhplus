package com.example.ticketing.domain.user.component;

public interface UserReader {

    /**
     * 잔액 조회
     */
    int findBalance(String userUUID);
}
