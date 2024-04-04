package com.example.ticketing.api.concert.controller;

import com.example.ticketing.api.concert.dto.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ConcertTicketingControllerTest {

    @InjectMocks
    private ConcertTicketingController target;

    private MockMvc mockMvc;
    private Gson gson;

    private final String userUUID = "1e9ebe68-045a-49f1-876e-a6ea6380dd5c";
    private final Long tokenId = 1L;
    private final LocalDateTime issuedAt = LocalDateTime.of(2024, 4, 10, 13, 30, 0);
    private int token;

    private final LocalDateTime concertDate = LocalDateTime.of(2024, 4, 20, 15, 0, 0);

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();

        token = issuedAt.hashCode();
        token = 31 * token + userUUID.hashCode();
        token = 31 * token + "IU_BLUEMING_001".hashCode();
    }

    /**
     * [대기열 토큰 발급 API]
     * case1: 대기열 토큰 발급 실패 - 존재하지 않는 콘서트
     * case2: 대기열 토큰 발급 실패 - 존재하지 않는 유저 UUID
     * case3: 대기열 토큰 발급 성공
     */

    @Test
    @DisplayName("대기열 토큰 발급 실패 - 존재하지 않는 콘서트")
    void case1() throws Exception {
        //given
        String url = "/api/v1/concerts/IU_BLUEMING_X/token";

        //when
        ResultActions resultActions = mockMvc.perform(post(url));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("대기열 토큰 발급 실패 - 존재하지 않는 유저 UUID")
    void case2() throws Exception {
        //given
        String url = "/api/v1/concerts/IU_BLUEMING_001/token";
        String nonUserUUID = UUID.randomUUID().toString();

        //when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(nonUserUUID))
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("대기열 토큰 발급 성공")
    void case3() throws Exception {
        //given
        String url = "/api/v1/concerts/IU_BLUEMING_001/token";

        //when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUUID)
        );

        //then
        MockHttpServletResponse response = resultActions
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // Gson Deserialize
        Type type = new TypeToken<ConcertApiResponse<IssuedTokenResponse>>() {}.getType();

        String contentAsString = response.getContentAsString();

        ConcertApiResponse<IssuedTokenResponse> result = gson.fromJson(contentAsString, type);
        IssuedTokenResponse content = result.content();
        assertThat(content).isNotNull();
        assertThat(content.tokenId()).isEqualTo(tokenId);
        assertThat(content.token()).isEqualTo(token);
    }

    /**
     * [예매 가능 날짜 조회 API]
     * case4: 예매 가능 날짜 조회 실패 - 존재하지 않는 콘서트
     * case5: 예매 가능 날짜 조회 실패 - 존재하지 않는 유저 UUID
     * case6: 예매 가능 날짜 조회 실패 - 현재 유저의 대기열 토큰과 일치하지 않음
     * case7: 예매 가능 날짜 조회 성공
     */

    @Test
    @DisplayName("예매 가능 날짜 조회 실패 - 존재하지 않는 콘서트")
    void case4() throws Exception {
        //given
        String url = "/api/v1/concerts/IU_BLUEMING_X";

        //when
        ResultActions resultActions = mockMvc.perform(get(url));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("예매 가능 날짜 조회 실패 - 존재하지 않는 유저 UUID")
    void case5() throws Exception {
        //given
        String url = "/api/v1/concerts/IU_BLUEMING_001";
        String nonUserUUID = UUID.randomUUID().toString();

        //when
        ResultActions resultActions = mockMvc.perform(
                get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nonUserUUID)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("예매 가능 날짜 조회 실패 - 현재 유저의 대기열 토큰과 일치하지 않음")
    void case6() throws Exception {
        //given
        String url = "/api/v1/concerts/IU_BLUEMING_001";

        //when
        ResultActions resultActions = mockMvc.perform(
                get(url)
                        .header("Authorization", "any token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUUID)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("예매 가능 날짜 조회 성공")
    void case7() throws Exception {
        //given
        String url = "/api/v1/concerts/IU_BLUEMING_001";

        //when
        ResultActions resultActions = mockMvc.perform(
                get(url)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUUID)
        );

        //then
        MockHttpServletResponse response = resultActions
                .andExpect(status().isOk())
                .andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");

        // Gson Deserialize
        Type type = new TypeToken<ConcertApiResponse<List<AvailableConcertDatesResponse>>>() {}.getType();

        String contentAsString = response.getContentAsString();
        ConcertApiResponse<List<AvailableConcertDatesResponse>> result = gson.fromJson(contentAsString, type);
        List<AvailableConcertDatesResponse> content = result.content();
        assertThat(content.size()).isEqualTo(2);
    }

    /**
     * [해당 날짜의 좌석 조회 API]
     * case8: 해당 날짜의 좌석 조회 실패 - 존재하지 않는 콘서트
     * case9: 해당 날짜의 좌석 조회 실패 - 존재하지 않는 유저 UUID
     * case10: 해당 날짜의 좌석 조회 실패 - 현재 유저의 대기열 토큰과 일치하지 않음
     * case11: 해당 날짜의 좌석 조회 성공
     */

    @Test
    @DisplayName("해당 날짜의 좌석 조회 실패 - 존재하지 않는 콘서트")
    void case8() throws Exception {
        //given
        String url = "/api/v1/concerts/IU_BLUEMING_X/seats";

        //when
        ResultActions resultActions = mockMvc.perform(get(url));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("해당 날짜의 좌석 조회 실패 - 존재하지 않는 유저 UUID")
    void case9() throws Exception {
        //given
        String url = "/api/v1/concerts/IU_BLUEMING_001/seats";
        String nonUserUUID = UUID.randomUUID().toString();

        //when
        ResultActions resultActions = mockMvc.perform(
                get(url)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(new FindConcertSeatsRequest(nonUserUUID, concertDate)))
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("해당 날짜의 좌석 조회 실패 - 현재 유저의 대기열 토큰과 일치하지 않음")
    void case10() throws Exception {
        //given
        String url = "/api/v1/concerts/IU_BLUEMING_001/seats";

        //when
        ResultActions resultActions = mockMvc.perform(
                get(url)
                        .header("Authorization", "any token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(new FindConcertSeatsRequest(userUUID, concertDate)))
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("해당 날짜의 좌석 조회 성공")
    void case11() throws Exception {
        //given
        String url = "/api/v1/concerts/IU_BLUEMING_001/seats";

        //when
        ResultActions resultActions = mockMvc.perform(
                get(url)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(new FindConcertSeatsRequest(userUUID, concertDate)))
        );

        //then
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andReturn().getResponse();

        // Gson Deserialize
        Type type = new TypeToken<ConcertApiResponse<List<AvailableConcertSeatsResponse>>>() {}.getType();

        String contentAsString = response.getContentAsString();

        ConcertApiResponse<List<AvailableConcertSeatsResponse>> result = gson.fromJson(contentAsString, type);
        List<AvailableConcertSeatsResponse> content = result.content();
        assertThat(content.size()).isEqualTo(5);
    }

}