package com.example.ticketing.api.user.usecase;

import com.example.ticketing.domain.user.entity.User;
import com.example.ticketing.domain.user.infrastructure.UserJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class ChargeBalanceUseCaseV2Test {

    @Autowired
    private ChargeBalanceUseCaseV2 chargeBalanceUseCaseV2;

    @Autowired
    private UserJpaRepository repository;

    @Test
    @DisplayName("잔액 충전 따닥 허용")
    void case1() throws Exception {
        String userUUID = UUID.randomUUID().toString();
        User createUser = User.builder()
                .userUUID(userUUID)
                .balance(30000)
                .build();
        User savedUser = repository.save(createUser);

        ExecutorService service = Executors.newFixedThreadPool(2);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                chargeBalanceUseCaseV2.execute(userUUID, 10000);
            }, service);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        service.shutdown();

        int findBalance = repository.findById(userUUID).get().getBalance();
        Assertions.assertThat(findBalance).isEqualTo(50000);
    }
}