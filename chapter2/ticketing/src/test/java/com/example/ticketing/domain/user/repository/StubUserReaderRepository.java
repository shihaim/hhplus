package com.example.ticketing.domain.user.repository;

import com.example.ticketing.domain.user.entity.User;

import java.util.Optional;

public class StubUserReaderRepository implements UserReaderRepository {
    @Override
    public Optional<User> findByUserUUID(String userUUID) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findBalanceByUserUUID(String userUUID) {
        if (!userUUID.equals("1e9ebe68-045a-49f1-876e-a6ea6380dd5c")) return Optional.empty(); // fake 일까?

        return Optional.of(User.builder().balance(30000).build());
    }
}
