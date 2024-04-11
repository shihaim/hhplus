package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.repository.ConcertReaderRepository;
import com.example.ticketing.domain.concert.repository.StubConcertReaderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConcertValidatorTest {

    private final ConcertValidator sut = new ConcertValidator();
    private final ConcertReaderRepository stubReaderRepository = new StubConcertReaderRepository();

    /**
     * [콘서트 Validator]
     * case1: 존재하지 않는 콘서트인 경우 Error Throw
     */

    @Test
    @DisplayName("존재하지 않는 콘서트인 경우 Error Throw")
    void case1() throws Exception {
        //given
        List<Concert> findConcert = stubReaderRepository.findAllByConcertCode(null);

        //when
        NoSuchElementException e = assertThrows(
                NoSuchElementException.class,
                () -> sut.isExist(findConcert.size())
        );

        //then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 콘서트입니다.");
    }
}