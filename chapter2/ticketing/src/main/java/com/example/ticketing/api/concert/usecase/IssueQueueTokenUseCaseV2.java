package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.IssuedTokenResponse;
import com.example.ticketing.domain.concert.component.ConcertReader;
import com.example.ticketing.domain.concert.component.ConcertValidator;
import com.example.ticketing.domain.token.component.QueueTokenReaderV2;
import com.example.ticketing.domain.token.component.QueueTokenStoreV2;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.entity.QueueTokenInfo;
import com.example.ticketing.domain.user.component.UserReader;
import com.example.ticketing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueQueueTokenUseCaseV2 {

    private final QueueTokenReaderV2 queueTokenReader;
    private final QueueTokenStoreV2 queueTokenStore;
    private final ConcertReader concertReader;
    private final ConcertValidator concertValidator;
    private final UserReader userReader;

    /**
     * 유저 토큰 발급
     */
    public IssuedTokenResponse execute(String concertCode, String userUUID) {
        // 1. 콘서트 존재 여부 확인
//        concertValidator.isExist(concertReader.findConcertDates(concertCode).size()); // TODO 캐시 처리하여서 존재 여부 확인하는 방식으로 가져가기
        // 2. 유저 존재 여부 확인후 토큰 발급
//        User user = userReader.findUser(userUUID);
        QueueTokenInfo createQueueTokenInfo = QueueTokenInfo.createQueueTokenInfo(concertCode, userUUID);
        queueTokenStore.saveQueueToken(createQueueTokenInfo);
        // 3. 대기열 토큰 발급후 Rank 조회
        queueTokenReader.findMyQueueRank(createQueueTokenInfo, true);

        return IssuedTokenResponse.convert2(createQueueTokenInfo);
    }
}
