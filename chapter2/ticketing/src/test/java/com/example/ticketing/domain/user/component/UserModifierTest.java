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
    private final UserModifier sut = new UserModifierImpl(stubStoreRepository);

    /**
     * [잔액 충전]
     * case1: 잔액 충전 실패 - 충전 금액이 음수
     * case2: 잔액 충전 실패 - 존재하지 않는 유저
     * case3: 잔액 충전 성공
     */

    @Test
    @DisplayName("잔액 충전 실패 - 충전 금액이 음수")
    void case1() throws Exception {
        //given
        String userUUID = "1e9ebe68-045a-49f1-876e-a6ea6380dd5c";
        int amount = -1;

        //when
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> sut.chargeBalance(userUUID, amount)
        );

        //then
        assertThat(e.getMessage()).isEqualTo("충전하려는 금액이 음수입니다.");
    }

    @Test
    @DisplayName("잔액 충전 실패 - 존재하지 않는 유저")
    void case2() throws Exception {
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
    void case3() throws Exception {
        //given
        String userUUID = "1e9ebe68-045a-49f1-876e-a6ea6380dd5c";
        int amount = 10000;

        //when
        int result = sut.chargeBalance(userUUID, amount);

        //then
        assertThat(result).isEqualTo(40000);
    }
}