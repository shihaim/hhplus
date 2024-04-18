package com.example.ticketing.domain.user.repository;

import com.example.ticketing.domain.user.entity.User;

import java.util.Optional;

public class StubUserReaderRepository implements UserReaderRepository {
    @Override
    public Optional<User> findByUserUUID(String userUUID) {
        if (!"1e9ebe68-045a-49f1-876e-a6ea6380dd5c".equals(userUUID)) return Optional.empty();

        return Optional.of(User.builder().userUUID("1e9ebe68-045a-49f1-876e-a6ea6380dd5c").balance(30000).build());
    }
}
