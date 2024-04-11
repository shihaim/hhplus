package com.example.ticketing.domain.user.component;

import com.example.ticketing.domain.user.entity.User;
import com.example.ticketing.domain.user.repository.UserReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserReaderRepository readerRepository;

    /**
     * TODO [TC] 유저 조회
     */
    public User findUser(String userUUID) {
        return readerRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));
    }

    /**
     * 잔액 조회
     */
    public int findBalance(String userUUID) {
        return readerRepository.findBalanceByUserUUID(userUUID)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."))
                .getBalance();
    }
}
