package com.example.ticketing.domain.user.component;

import com.example.ticketing.domain.concert.repository.ConcertReaderRepository;
import com.example.ticketing.domain.concert.repository.StubConcertReaderRepository;
import com.example.ticketing.domain.user.repository.StubUserReaderRepository;
import com.example.ticketing.domain.user.repository.UserReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserValidatorTest {

    private final UserValidator sut = new UserValidator();
    private final ConcertReaderRepository stubConcertReaderRepository = new StubConcertReaderRepository();
    private final UserReaderRepository stubUserReaderRepository = new StubUserReaderRepository();

    /**
     * [유저 Validator]
     * case1: 충전하려는 금액이 음수일 경우 Error Throw
     * case2: 콘서트의 가격보다 잔액이 적은 경우 Error Throw
     */

    @Test
    @DisplayName("충전하려는 금액이 음수일 경우 Error Throw")
    void case1() throws Exception {
        //given
        int amount = -1;

        //when
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> sut.isNegativeAmount(amount)
        );

        //then
        assertThat(e.getMessage()).isEqualTo("충전하려는 금액이 음수입니다.");
    }

    @Test
    @DisplayName("충전하려는 금액이 음수일 경우 Error Throw")
    void case2() throws Exception {
        //given
        int price = stubConcertReaderRepository.findByConcertPK(Mockito.any()).get().getPrice();
        int balance = stubUserReaderRepository.findByUserUUID("1e9ebe68-045a-49f1-876e-a6ea6380dd5c").get().getBalance();

        //when
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> sut.isLessThanPrice(price, balance)
        );

        //then
        assertThat(e.getMessage()).isEqualTo("콘서트의 가격보다 잔액이 적습니다.");
    }
}