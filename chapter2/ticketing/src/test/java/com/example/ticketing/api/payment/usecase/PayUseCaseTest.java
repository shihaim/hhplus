package com.example.ticketing.api.payment.usecase;

import com.example.ticketing.api.payment.dto.PaymentDetailResponse;
import com.example.ticketing.domain.concert.entity.*;
import com.example.ticketing.domain.concert.infrastructure.ConcertJpaRepository;
import com.example.ticketing.domain.concert.infrastructure.ReservationJpaRepository;
import com.example.ticketing.domain.concert.infrastructure.SeatJpaRepository;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.infrastructure.QueueTokenJpaRepository;
import com.example.ticketing.domain.user.entity.User;
import com.example.ticketing.domain.user.infrastructure.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * PayUseCase 통합 테스트
 */
@Transactional
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PayUseCaseTest {

    @Autowired
    private PayUseCase payUseCase;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private QueueTokenJpaRepository queueTokenJpaRepository;

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private SeatJpaRepository seatJpaRepository;

    @Autowired
    private ReservationJpaRepository reservationJpaRepository;

    private final String concertCode = "IU_BLUEMING_001";
    private final LocalDateTime concertDate = LocalDateTime.of(2024, 5, 10, 15, 30, 0);
    private final String userUUID = UUID.randomUUID().toString();
    private final String anotherUserUUID = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        Concert createConcert = Concert.builder()
                .concertCode(concertCode)
                .concertDate(concertDate)
                .concertName("아이유 블루밍 콘서트")
                .price(79900)
                .build();
        Concert savedConcert = concertJpaRepository.save(createConcert);

        User createUser = User.builder()
                .userUUID(userUUID)
                .balance(80000)
                .build();
        User savedUser = userJpaRepository.save(createUser);

        QueueToken createQueueToken = QueueToken.createQueueToken(concertCode, createUser);
        QueueToken savedQueueToken = queueTokenJpaRepository.save(createQueueToken);

        User createAnotherUser = User.builder()
                .userUUID(anotherUserUUID)
                .balance(40000)
                .build();
        User savedAnotherUser = userJpaRepository.save(createAnotherUser);

        QueueToken createAnotherQueueToken = QueueToken.createQueueToken(concertCode, createAnotherUser);
        queueTokenJpaRepository.save(createAnotherQueueToken);

        for (int i = 1; i <= 10; i++) {
            Seat createSeat = Seat.builder()
                    .concert(savedConcert)
                    .seatNumber(i)
                    .status(TicketingStatus.NONE)
                    .build();

            Seat savedSeat = seatJpaRepository.save(createSeat);

            Reservation createReservation = Reservation.builder()
                    .seat(savedSeat)
                    .status(AssignmentStatus.NOT_BE_ASSIGNED)
                    .build();

            if (i == 10) {
                createReservation.assignment(userUUID, savedQueueToken.getToken());
            }

            reservationJpaRepository.save(createReservation);
        }
    }

//    @Test
    @DisplayName("결제 요청 통합 테스트")
    void case1() throws Exception {
        //given
        List<QueueToken> findQueueTokens = queueTokenJpaRepository.findAll();
        User user = userJpaRepository.findById(userUUID).get();
        QueueToken findQueueToken = user.getQueueToken();

        //when
        PaymentDetailResponse result = payUseCase.execute(user.getUserUUID(), findQueueToken.getToken());
        Reservation findReservation = reservationJpaRepository.findById(10L).get();
        Seat findSeat = findReservation.getSeat();

        //then
        assertThat(result.userUUID()).isEqualTo(findReservation.getUserUUID());
        assertThat(result.seatNumber()).isEqualTo(findSeat.getSeatNumber());
        assertThat(findReservation.getStatus()).isEqualTo(AssignmentStatus.ASSIGNED);
    }
}