package com.example.ticketing.api.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController target;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder().create();
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    /**
     * [잔액 조회 API]
     * case1: 잔액 조회 실패 - 존재하지 않는 유저
     * case2: 잔액 조회 성공
     */

    @Test
    @DisplayName("잔액 조회 실패 - 존재하지 않는 유저")
    void case1() throws Exception {
        //given
        String url = "/api/v1/users/nonUserUUID/balance";

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("잔액 조회 성공")
    void case2() throws Exception {
        //given
        String url = "/api/v1/users/1e9ebe68-045a-49f1-876e-a6ea6380dd5c/balance";

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));

        //then
        resultActions.andExpect(status().isOk());
    }

    /**
     * [잔액 충전 API]
     * case3: 잔액 충전 실패 - 존재하지 않는 유저
     * case4: 잔액 충전 실패 - 충전 금액이 음수
     * case5: 잔액 충전 성공
     */
    @Test
    @DisplayName("잔액 충전 실패 - 존재하지 않는 유저")
    void case3() throws Exception {
        //given
        String url = "/api/v1/users/nonUserUUID/balance";

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch(url));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("잔액 충전 실패 - 충전 금액이 음수")
    void case4() throws Exception {
        //given
        String url = "/api/v1/users/1e9ebe68-045a-49f1-876e-a6ea6380dd5c/balance";

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("-1")
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("잔액 충전 성공")
    void case5() throws Exception {
        //given
        String url = "/api/v1/users/1e9ebe68-045a-49f1-876e-a6ea6380dd5c/balance";

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("50000")
        );

        //then
        resultActions.andExpect(status().isOk());
    }
}
