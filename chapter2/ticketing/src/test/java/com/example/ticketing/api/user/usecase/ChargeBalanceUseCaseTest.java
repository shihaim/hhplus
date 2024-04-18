package com.example.ticketing.api.user.usecase;

import com.example.ticketing.domain.user.entity.User;
import com.example.ticketing.domain.user.infrastructure.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ChargeBalanceUseCase 통합 테스트
 */
@SpringBootTest
class ChargeBalanceUseCaseTest {

    @Autowired
    private ChargeBalanceUseCase chargeBalanceUseCase;

    @Autowired
    private UserJpaRepository repository;

    @Test
    @DisplayName("잔액 충전 통합 테스트")
    void case1() throws Exception {
        //given
        String userUUID = UUID.randomUUID().toString();
        User createUser = User.builder()
                .userUUID(userUUID)
                .balance(30000)
                .build();
        User savedUser = repository.save(createUser);

        int amount = 20000;

        //when
        int balance = chargeBalanceUseCase.execute(userUUID, amount);

        //then
        assertThat(userUUID).isEqualTo(savedUser.getUserUUID());
        assertThat(balance).isEqualTo(savedUser.getBalance() + amount);
    }
}