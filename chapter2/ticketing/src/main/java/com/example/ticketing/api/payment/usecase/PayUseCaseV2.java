package com.example.ticketing.api.payment.usecase;

import com.example.ticketing.api.payment.dto.PaymentDetailResponse;
import com.example.ticketing.domain.concert.component.ConcertReservationReader;
import com.example.ticketing.domain.concert.component.ConcertReservationValidator;
import com.example.ticketing.domain.concert.entity.Concert;
import com.example.ticketing.domain.concert.entity.Reservation;
import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.handler.lock.LockHandler;
import com.example.ticketing.domain.handler.transaction.TransactionHandler;
import com.example.ticketing.domain.payment.component.PaymentDetailStore;
import com.example.ticketing.domain.payment.entity.PaymentDetail;
import com.example.ticketing.domain.user.component.UserReader;
import com.example.ticketing.domain.user.component.UserValidator;
import com.example.ticketing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayUseCaseV2 {
    private final static String PAY_USE_CASE_LOCK_PREFIX = "PAY_";
    private final LockHandler lockHandler;
    private final TransactionHandler transactionHandler;

    private final UserReader userReader;
    private final UserValidator userValidator;
    private final ConcertReservationReader concertReservationReader;
    private final ConcertReservationValidator concertReservationValidator;
    private final PaymentDetailStore paymentDetailStore;

    public PaymentDetailResponse execute(String userUUID, int token) {

        PaymentDetailResponse result = lockHandler.runOnLock(PAY_USE_CASE_LOCK_PREFIX + userUUID, 0L, 3L, "이미 결제가 진행 중입니다.",
                () -> transactionHandler.runOnWriteTransaction(() -> {
                    // 1. 유저 존재 여부 체크
                    User findUser = userReader.findUser(userUUID);
                    // 2. 임시 배정된 좌석 존재 여부 체크
                    Reservation findReservation = concertReservationReader.findAssignedSeat(userUUID, token);
                    // 3. 임시 배정된 시간이 만료되었는지 체크
                    concertReservationValidator.isExpired(findReservation.getAssignedAt());
                    // 4. 콘서트 가격과 잔액 비교
                    Seat findSeat = findReservation.getSeat();
                    Concert findConcert = findSeat.getConcert();
                    userValidator.isLessThanPrice(findConcert.getPrice(), findUser.getBalance());
                    // 5. 결제 진행
                    PaymentDetail savePaymentDetail = paymentDetailStore.savePaymentDetail(PaymentDetail.createPaymentDetail(findUser, findConcert, findReservation));
                    // 6. 잔액 감소
                    findUser.reduceBalance(findConcert.getPrice());
                    // 7. 좌석 예매 완료 처리
                    findSeat.ticketingComplete();
                    // 8. 대기열 토큰 제거 (soft delete)
                    findUser.getQueueToken().changeTokenToExpired(); // TODO redis내 토큰 삭제로 변경 필요

                    return PaymentDetailResponse.convert(savePaymentDetail);
        }));

        return result;
    }
}
