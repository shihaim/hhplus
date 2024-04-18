package com.example.ticketing.domain.user.repository;

import com.example.ticketing.domain.user.entity.User;

import java.util.Optional;

public interface UserReaderRepository {

    Optional<User> findByUserUUID(String userUUID);
}
