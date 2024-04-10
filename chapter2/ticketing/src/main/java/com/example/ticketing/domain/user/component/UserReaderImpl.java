package com.example.ticketing.domain.user.component;

import com.example.ticketing.domain.user.repository.UserReaderRepository;

import java.util.NoSuchElementException;

public class UserReaderImpl implements UserReader {

    private final UserReaderRepository readerRepository;

    public UserReaderImpl(UserReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public int findBalance(String userUUID) {
        return readerRepository.findBalanceByUserUUID(userUUID)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."))
                .getBalance();
    }
}
