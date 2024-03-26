package io.hhplus.step2.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.hhplus.step2.lecture.common.ReservationResponse;
import io.hhplus.step2.lecture.exception.GlobalExceptionHandler;
import io.hhplus.step2.lecture.service.dto.FindLectureDto;
import io.hhplus.step2.lecture.web.LectureReservationController;
import io.hhplus.step2.lecture.web.dto.CreateLectureReservationDto;
import io.hhplus.step2.lecture.web.dto.LectureSearchDto;
import io.hhplus.step2.sevice.stub.LectureReservationManagerStub;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LectureReservationControllerTest {

    @InjectMocks
    private LectureReservationController target;

    @Mock
    private LectureReservationManagerStub managerStub;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    /**
     * [특강 신청]
     * case1: 파라미터 검증( PathVariable, ReservationCreateDto )
     * case2: 특강 신청 API 성공
     */

    @Test
    @DisplayName("특강신청실패 - 파라미터 오류( PathVariable )")
    void case1_PathVariable() throws Exception {
        //given
        String url = "/api/v1/lectures/-1";
        Long lectureId = 1L;
        LocalDateTime reservationDate = LocalDateTime.now();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(new CreateLectureReservationDto(lectureId, reservationDate)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("invalidReservationCreateDtoParameter")
    @DisplayName("특강신청실패 - 파라미터 오류( ReservationCreateDto )")
    void case1_ReservationCreateDto(Long lectureId, LocalDateTime reservationDate) throws Exception {
        // given
        String url = "/api/v1/lectures/1";

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(new CreateLectureReservationDto(lectureId, reservationDate)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> invalidReservationCreateDtoParameter() {
        return Stream.of(
                Arguments.of(null, LocalDateTime.now()),
                Arguments.of(-1L, LocalDateTime.now()),
                Arguments.of(1L, null),
                Arguments.of(1L, LocalDateTime.of(2024,1,1,12,0))
        );
    }

    @Test
    @DisplayName("특강신청성공")
    void case2() throws Exception {
        //given
        String url = "/api/v1/lectures/1";
        Long userId = 1L;
        Long lectureId = 1L;

        LocalDateTime reservationDate = LocalDateTime.now();

        doReturn(null).when(managerStub)
                .lectureReservation(userId, lectureId, reservationDate);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(new CreateLectureReservationDto(lectureId, reservationDate)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void dateTest() throws Exception {
        //given
        LocalDateTime openDate = LocalDateTime.of(2024, 4, 20, 13, 0, 0);
        LocalDateTime reservationDate = LocalDateTime.of(2024, 4, 20, 14, 0, 0);

        //when
        String result1 = openDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
        String result2 = reservationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));

        //then
        System.out.println(result1.equals(result2));
        System.out.println(result1.compareTo(result2));
    }

    /**
     * [특강 신청 완료 여부 조회]
     * case3: 파라미터 검증( PathVariable )
     * case4: 특강 신청 API 성공 (성공했음/실패했음)
     */

    @Test
    @DisplayName("특강신청완료여부조회실패 - 파라미터 오류( PathVariable )")
    void case3_PathVariable() throws Exception {
        //given
        String url = "/api/v1/lectures/-1";

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("특강신청완료여부조회성공 - 성공했음")
    void case4_성공했음() throws Exception {
        //given
        Long userId = 1L;
        String url = "/api/v1/lectures/1";

        String findResult = "성공했음";
        doReturn(findResult).when(managerStub).findReservedLecture(userId);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        String result = convertStringToUtf8(resultActions);

        //then
        Assertions.assertThat(result).isEqualTo(findResult);
    }

    @Test
    @DisplayName("특강신청완료여부조회성공 - 실패했음")
    void case4_실패했음() throws Exception {
        //given
        Long userId = 2L;
        String url = "/api/v1/lectures/2";

        String findResult = "실패했음";
        doReturn(findResult).when(managerStub).findReservedLecture(userId);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        String result = convertStringToUtf8(resultActions);

        //then
        Assertions.assertThat(result).isEqualTo(findResult);
    }

    // MockHttpServletResponse의 CharacterEncoding이 ISO-8859-1이므로 UTF-8로 변환해줘야함.
    private String convertStringToUtf8(ResultActions resultActions) throws Exception {
        MockHttpServletResponse response = resultActions.andExpect(status().isOk()).andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        String contentAsString = response.getContentAsString();

        ReservationResponse result = gson.fromJson(contentAsString, ReservationResponse.class);
        String content = (String) result.getContent();

        return content;
    }

    /**
     * [특강 목록 조회]
     * case7: 특강 목록 조회 API 성공 (파라미터 검증 X - Null 허용)
     */

    @Test
    @DisplayName("특강목록조회성공")
    void case7() throws Exception {
        // given
        String url = "/api/v1/lectures";

        List<FindLectureDto> result = List.of(
                new FindLectureDto("항플 특강1", 10, LocalDateTime.of(2024, 4, 20, 13, 0, 0)),
                new FindLectureDto("항플 특강2", 10, LocalDateTime.of(2024, 4, 21, 13, 0, 0)),
                new FindLectureDto("항플 특강3", 10, LocalDateTime.of(2024, 4, 22, 13, 0, 0))
        );

        LocalDateTime searchFromDate = LocalDateTime.of(2024, 4, 20, 0, 0, 0);
        LocalDateTime searchToDate = LocalDateTime.of(2024, 4, 22, 0, 0, 0);

        doReturn(result).when(managerStub).findLectureList(searchFromDate, searchToDate);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .content(gson.toJson(new LectureSearchDto(searchFromDate, searchToDate)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());
    }
}
