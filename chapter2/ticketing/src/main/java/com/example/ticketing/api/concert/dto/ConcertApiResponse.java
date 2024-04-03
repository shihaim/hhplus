package com.example.ticketing.api.concert.dto;

public record ConcertApiResponse<T>(
        T content
) {
    public static <T> ConcertApiResponse<T> of(T result) {
        return new ConcertApiResponse<>(result);
    }
}
