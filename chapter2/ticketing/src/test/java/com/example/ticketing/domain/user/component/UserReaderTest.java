package com.example.ticketing.domain.user.component;

import com.example.ticketing.domain.user.repository.StubUserReaderRepository;
import com.example.ticketing.domain.user.repository.UserReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserReaderTest {

    private final UserReaderRepository stubReaderRepository = new StubUserReaderRepository();
    private final UserReader sut = new UserReaderImpl(stubReaderRepository);

    /**
     * [잔액 조회]
     * case1: 잔액 조회 실패 - 존재하지 않는 유저
     * case2: 잔액 조회 성공
     */

    @Test
    @DisplayName("잔액 조회 실패 - 존재하지 않는 유저")
    void case1() throws Exception {
        //given
        String userUUID = UUID.randomUUID().toString();

        //when
        NoSuchElementException e = assertThrows(
                NoSuchElementException.class,
                () -> sut.findBalance(userUUID)
        );

        //then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("잔액 조회 성공")
    void case2() throws Exception {
        //given
        String userUUID = "1e9ebe68-045a-49f1-876e-a6ea6380dd5c";

        //when
        int findBalance = sut.findBalance(userUUID);

        //then
        assertThat(findBalance).isEqualTo(30000);
    }
}