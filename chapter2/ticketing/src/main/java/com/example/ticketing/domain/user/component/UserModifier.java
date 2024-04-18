package com.example.ticketing.domain.user.component;

import com.example.ticketing.domain.user.entity.User;
import com.example.ticketing.domain.user.repository.UserReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class UserModifier {

    private final UserReaderRepository readerRepository;

    /**
     * 잔액 충전
     */
    public int chargeBalance(String userUUID, int amount) {
        User findUser = readerRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));

        return findUser.chargeBalance(amount);
    }
}
