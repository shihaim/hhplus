package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenReaderRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class QueueTokenReader {

    private final QueueTokenReaderRepository readerRepository;

    public QueueTokenReader(QueueTokenReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    /**
     * 대기열 토큰 조회
     */
    public QueueToken findQueueToken(String userUUID) {
        return readerRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 토큰입니다."));
    }
}
