package com.example.ticketing.api.payment.usecase;

import com.example.ticketing.domain.concert.entity.*;
import com.example.ticketing.domain.concert.infrastructure.ConcertJpaRepository;
import com.example.ticketing.domain.concert.infrastructure.ReservationJpaRepository;
import com.example.ticketing.domain.concert.infrastructure.SeatJpaRepository;
import com.example.ticketing.domain.payment.entity.PaymentDetail;
import com.example.ticketing.domain.payment.infrastructure.PaymentDetailJpaRepository;
import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.domain.token.infrastructure.QueueTokenJpaRepository;
import com.example.ticketing.domain.user.entity.User;
import com.example.ticketing.domain.user.infrastructure.UserJpaRepository;
import jakarta.persistence.OptimisticLockException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class PayUseCaseV2Test {

    @Autowired
    private PayUseCaseV2 payUseCaseV2;

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

    @Autowired
    private PaymentDetailJpaRepository paymentDetailJpaRepository;

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
                .balance(180000)
                .build();
        User savedUser = userJpaRepository.save(createUser);

        QueueToken createQueueToken = QueueToken.createQueueToken(concertCode, createUser);
        QueueToken savedQueueToken = queueTokenJpaRepository.save(createQueueToken);

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

    @Test
    @DisplayName("결제 따닥 막기")
    void case1() throws Exception {
        QueueToken findQueueToken = queueTokenJpaRepository.findAll().get(0);

        ExecutorService service = Executors.newFixedThreadPool(100);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        AtomicInteger successCnt = new AtomicInteger();
        AtomicInteger failCnt = new AtomicInteger();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    payUseCaseV2.execute(userUUID, findQueueToken.getToken());
                    successCnt.incrementAndGet();
                } catch (OptimisticLockException e) {
                    failCnt.incrementAndGet();
                }
            }, service);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        service.shutdown();

        List<PaymentDetail> paymentDetails = paymentDetailJpaRepository.findAll();
        int findBalance = userJpaRepository.findById(userUUID).get().getBalance();

        Assertions.assertThat(successCnt.get()).as("성공의 개수").isEqualTo(1L);
        Assertions.assertThat(failCnt.get()).as("실패의 개수").isEqualTo(4L);
        Assertions.assertThat(paymentDetails.size()).isEqualTo(1);
        Assertions.assertThat(findBalance).isEqualTo(180000 - 79900);
    }
}