package com.example.ticketing.domain.concert.component;

import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ConcertValidator {

    public void isExist(int size) {
        if (size == 0) {
            throw new NoSuchElementException("존재하지 않는 콘서트입니다.");
        }
    }
}
