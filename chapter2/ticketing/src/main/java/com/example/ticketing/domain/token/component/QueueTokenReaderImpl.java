package com.example.ticketing.domain.token.component;

import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.repository.QueueTokenReaderRepository;

import java.util.NoSuchElementException;

public class QueueTokenReaderImpl implements QueueTokenReader {

    private final QueueTokenReaderRepository readerRepository;

    public QueueTokenReaderImpl(QueueTokenReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public QueueToken findQueueToken(String userUUID) {
        return readerRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 토큰입니다."));
    }
}
