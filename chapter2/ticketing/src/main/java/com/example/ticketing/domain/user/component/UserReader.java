package com.example.ticketing.domain.user.component;

import com.example.ticketing.domain.user.repository.UserReaderRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class UserReader {

    private final UserReaderRepository readerRepository;

    public UserReader(UserReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
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
