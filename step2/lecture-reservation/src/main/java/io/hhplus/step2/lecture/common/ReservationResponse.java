package io.hhplus.step2.lecture.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationResponse<T> {

    @Getter
    private T content;
    private HttpStatus status;

    public static <T> ReservationResponse create(T content, HttpStatus status) {
        return new ReservationResponse(content, status);
    }
}
