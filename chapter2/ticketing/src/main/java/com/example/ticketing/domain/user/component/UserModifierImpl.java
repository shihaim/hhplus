package com.example.ticketing.domain.user.component;

import com.example.ticketing.domain.user.entity.User;
import com.example.ticketing.domain.user.repository.UserReaderRepository;

import java.util.NoSuchElementException;

public class UserModifierImpl implements UserModifier {

    private final UserReaderRepository readerRepository;

    public UserModifierImpl(UserReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public int chargeBalance(String userUUID, int amount) {
        if (amount < 0) throw new IllegalArgumentException("충전하려는 금액이 음수입니다.");

        User findUser = readerRepository.findBalanceByUserUUID(userUUID)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));

        return findUser.chargeBalance(amount);
    }
}