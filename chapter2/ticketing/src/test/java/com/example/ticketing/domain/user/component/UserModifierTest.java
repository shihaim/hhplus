package com.example.ticketing.domain.user.component;

import com.example.ticketing.domain.user.repository.StubUserReaderRepository;
import com.example.ticketing.domain.user.repository.UserReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserModifierTest {

    private final UserReaderRepository stubStoreRepository = new StubUserReaderRepository();
    private final UserModifier sut = new UserModifier(stubStoreRepository);

    /**
     * [잔액 충전]
     * case1: 잔액 충전 실패 - 존재하지 않는 유저
     * case2: 잔액 충전 성공
     */

    @Test
    @DisplayName("잔액 충전 실패 - 존재하지 않는 유저")
    void case1() throws Exception {
        //given
        String userUUID = UUID.randomUUID().toString();
        int amount = 10000;

        //when
        NoSuchElementException e = assertThrows(
                NoSuchElementException.class,
                () -> sut.chargeBalance(userUUID, amount)
        );

        //then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("잔액 충전 성공")
    void case2() throws Exception {
        //given
        String userUUID = "1e9ebe68-045a-49f1-876e-a6ea6380dd5c";
        int amount = 10000;

        //when
        int result = sut.chargeBalance(userUUID, amount);

        //then
        assertThat(result).isEqualTo(40000);
    }
}