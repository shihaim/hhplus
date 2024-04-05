package com.example.ticketing.api.payment.controller;

import com.example.ticketing.api.payment.usecase.StubPaymentAssignedSeatUseCase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @InjectMocks
    private PaymentController target;

    @Mock
    private StubPaymentAssignedSeatUseCase paymentUseCaseStub;

    private MockMvc mockMvc;
    private Gson gson;

    private final String userUUID = "1e9ebe68-045a-49f1-876e-a6ea6380dd5c";
    private final LocalDateTime issuedAt = LocalDateTime.of(2024, 4, 10, 13, 30, 0);
    private int token;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder().create();
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();

        token = issuedAt.hashCode();
        token = 31 * token + userUUID.hashCode();
        token = 31 * token + "IU_BLUEMING_001".hashCode();
    }

    /**
     * [결제 API]
     * case1: 결제 실패 - 존재하지 않는 유저
     * case2: 결제 실패 - 현재 유저의 대기열 토큰과 일치하지 않음
     * case3: 결제 실패 - 임시 배정된 좌석이 존재하지 않음
     * case4: 결제 실패 - 배정 시간이 만료됨
     * case5: 결제 실패 - 충전한 잔액이 콘서트 가격보다 적음
     * case6: 결제 성공
     */
    @Test
    @DisplayName("결제 실패 - 존재하지 않는 유저")
    void case1() throws Exception {
        //given
        String url = "/api/v1/payments";
        String nonUserUUID = UUID.randomUUID().toString();

        //when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nonUserUUID)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("결제 실패 - 현재 유저의 대기열 토큰과 일치하지 않음")
    void case2() throws Exception {
        //given
        String url = "/api/v1/payments";

        //when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .header("Authorization", "any token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUUID)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("결제 실패 - 임시 배정된 좌석이 존재하지 않음")
    void case3() throws Exception {
        //given
        String url = "/api/v1/payments";

        //when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUUID)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("결제 실패 - 배정 시간이 만료됨")
    void case4() throws Exception {
        //given
        String url = "/api/v1/payments";

        //when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUUID)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("결제 실패 - 충전한 잔액이 콘서트 가격보다 적음")
    void case5() throws Exception {
        //given
        String url = "/api/v1/payments";

        //when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUUID)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("결제 성공")
    void case6() throws Exception {
        //given
        String url = "/api/v1/payments";

        //when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUUID)
        );

        //then
    }
}
