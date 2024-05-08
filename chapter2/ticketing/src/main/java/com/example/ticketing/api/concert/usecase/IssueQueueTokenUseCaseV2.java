package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.IssuedTokenResponse;
import com.example.ticketing.domain.concert.component.ConcertReader;
import com.example.ticketing.domain.concert.component.ConcertValidator;
import com.example.ticketing.domain.token.component.QueueTokenStoreV2;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.user.component.UserReader;
import com.example.ticketing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueQueueTokenUseCaseV2 {

    private final QueueTokenStoreV2 queueTokenStore;
    private final ConcertReader concertReader;
    private final ConcertValidator concertValidator;
    private final UserReader userReader;

    /**
     * 유저 토큰 발급
     */
    public IssuedTokenResponse execute(String concertCode, String userUUID) {
        // 1. 콘서트 존재 여부 확인
        concertValidator.isExist(concertReader.findConcertDates(concertCode).size());
        // 2. 유저 존재 여부 확인후 토큰 발급
        User user = userReader.findUser(userUUID);
        return IssuedTokenResponse.convert2(queueTokenStore.saveQueueToken(QueueToken.createQueueToken(concertCode, userUUID)));
    }
}
