package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.IssuedTokenResponse;
import com.example.ticketing.domain.concert.component.ConcertReader;
import com.example.ticketing.domain.concert.component.ConcertValidator;
import com.example.ticketing.domain.token.component.QueueTokenStore;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.user.component.UserReader;
import com.example.ticketing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueQueueTokenUseCase {

    private final QueueTokenStore queueTokenStore;
    private final ConcertReader concertReader;
    private final ConcertValidator concertValidator;
    private final UserReader userReader;

    /**
     * 유저 토큰 발급
     */
    public IssuedTokenResponse execute(String concertCode, String userUUID) {
        concertValidator.isExist(concertReader.findConcertDates(concertCode).size());
        User user = userReader.findUser(userUUID);
        return IssuedTokenResponse.convert(queueTokenStore.saveQueueToken(QueueToken.createQueueToken(concertCode, user)));
    }
}
