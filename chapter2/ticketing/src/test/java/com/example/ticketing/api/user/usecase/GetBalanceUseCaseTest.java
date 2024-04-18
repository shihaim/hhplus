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
 * GetBalanceUseCase 통합 테스트
 */
@SpringBootTest
class GetBalanceUseCaseTest {

    @Autowired
    private GetBalanceUseCase getBalanceUseCase;

    @Autowired
    private UserJpaRepository repository;

    @Test
    @DisplayName("잔액 조회 통합 테스트")
    void case1() throws Exception {
        //given
        String userUUID = UUID.randomUUID().toString();
        User createUser = User.builder()
                .userUUID(userUUID)
                .balance(30000)
                .build();
        User savedUser = repository.save(createUser);

        //when
        int balance = getBalanceUseCase.execute(userUUID);

        //then
        assertThat(userUUID).isEqualTo(savedUser.getUserUUID());
        assertThat(balance).isEqualTo(savedUser.getBalance());
    }
}