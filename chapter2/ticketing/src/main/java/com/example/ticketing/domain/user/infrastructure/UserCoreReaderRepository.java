package com.example.ticketing.domain.user.infrastructure;

import com.example.ticketing.domain.user.entity.User;
import com.example.ticketing.domain.user.repository.UserReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCoreReaderRepository implements UserReaderRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByUserUUID(String userUUID) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findBalanceByUserUUID(String userUUID) {
        return Optional.empty();
    }
}
