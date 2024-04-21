package com.example.ticketing.api.concert.usecase;

import com.example.ticketing.api.concert.dto.ReservationResponse;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ReserveSeatUseCase 통합 테스트
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReserveSeatUseCaseTest {

    @Autowired
    private ReserveSeatUseCase reserveSeatUseCase;

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

            reservationJpaRepository.save(createReservation);
        }

        User createUser = User.builder()
                .userUUID(userUUID)
                .balance(30000)
                .build();
        userJpaRepository.save(createUser);

        QueueToken createQueueToken = QueueToken.createQueueToken(concertCode, createUser);
        queueTokenJpaRepository.save(createQueueToken);

        User createAnotherUser = User.builder()
                .userUUID(anotherUserUUID)
                .balance(40000)
                .build();
        userJpaRepository.save(createAnotherUser);

        QueueToken createAnotherQueueToken = QueueToken.createQueueToken(concertCode, createAnotherUser);
        queueTokenJpaRepository.save(createAnotherQueueToken);
    }

    @Test
    @DisplayName("좌석 예매 요청 통합 테스트")
    void case1() throws Exception {
        //given
        QueueToken findQueueToken = userJpaRepository.findById(userUUID).get().getQueueToken();

        //when
        ReservationResponse result = reserveSeatUseCase.execute(userUUID, findQueueToken.getToken(), concertCode, concertDate, 10);
        Reservation findReservation = reservationJpaRepository.findByUserUUIDAndToken(userUUID, findQueueToken.getToken()).get();

        //then
        assertThat(findReservation.getUserUUID()).isEqualTo(userUUID);
        assertThat(findReservation.getToken()).isEqualTo(findQueueToken.getToken());
        assertThat(findReservation.getStatus()).isEqualTo(AssignmentStatus.ASSIGNED);
        assertThat(findReservation.getAssignedAt()).isNotNull();
    }

    @Test
    @DisplayName("동시에 다른 유저들이 좌석 임시 배정을 진행")
    void case2() throws Exception {
        //given
        List<User> findUsers = userJpaRepository.findAll();

        ExecutorService service = Executors.newFixedThreadPool(2);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        findUsers.stream()
                .forEach(u -> {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        try {
                            reserveSeatUseCase.execute(u.getUserUUID(), u.getQueueToken().getToken(), concertCode, concertDate, 10);
                            successCount.incrementAndGet();
                        } catch (Exception e) { // OptimisticLockingFailureException 발생
                            failCount.incrementAndGet();
                        }
                    }, service);
                    futures.add(future);
                });

        //when
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        service.shutdown();

        //then
        assertThat(successCount.get()).as("유저 하나는 성공").isEqualTo(1);
        assertThat(failCount.get()).as("유저 하나는 실패").isEqualTo(1);
    }
}