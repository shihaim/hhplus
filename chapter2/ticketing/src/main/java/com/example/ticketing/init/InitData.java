package com.example.ticketing.init;

import com.example.ticketing.domain.concert.entity.*;
import com.example.ticketing.domain.concert.infrastructure.ConcertJpaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Component
public class InitData {

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private int batchSize = 1000;

    @PostConstruct
    public void init() {
        String concertCode1 = "IU_00";

        String concertCode2 = "BTS_00";

        String concertCode3 = "IVE_00";

        Long id = 0L;
        for (int i = 0; i < 3; i++) {
            LocalDateTime concertDate1 = LocalDateTime.of(2024, 5, 18 + i, 15, 30, 0);
            Concert createConcert1 = Concert.builder()
                    .concertCode(concertCode1 + (i+1))
                    .concertDate(concertDate1)
                    .concertName("아이유 콘서트" + (i+1))
                    .price(79900)
                    .build();

            concertJpaRepository.save(createConcert1);

            LocalDateTime concertDate2 = LocalDateTime.of(2024, 6, 10 + i, 15, 30, 0);
            Concert createConcert2 = Concert.builder()
                    .concertCode(concertCode2 + (i+1))
                    .concertDate(concertDate2)
                    .concertName("BTS 콘서트" + (i+1))
                    .price(89900)
                    .build();

            concertJpaRepository.save(createConcert2);

            LocalDateTime concertDate3 = LocalDateTime.of(2024, 10, 13 + i, 15, 30, 0);
            Concert createConcert3 = Concert.builder()
                    .concertCode(concertCode3 + (i+1))
                    .concertDate(concertDate3)
                    .concertName("아이브 콘서트" + (i+1))
                    .price(69900)
                    .build();

            concertJpaRepository.save(createConcert3);



            List<Seat> createSeats = new ArrayList<>();
            List<Reservation> createReservations = new ArrayList<>();

            for (int j = 0; j < 10000; j++) {
                Seat seat1 = Seat.builder()
                        .seatId(id)
                        .concert(createConcert1)
                        .seatNumber(j)
                        .status(TicketingStatus.NONE)
                        .build();
                createSeats.add(seat1);

                Reservation reservation1 = Reservation.builder()
                        .reservationId(id++)
                        .seat(seat1)
                        .status(AssignmentStatus.NOT_BE_ASSIGNED)
                        .version(0L)
                        .build();
                createReservations.add(reservation1);

                Seat seat2 = Seat.builder()
                        .seatId(id)
                        .concert(createConcert2)
                        .seatNumber(j)
                        .status(TicketingStatus.NONE)
                        .build();
                createSeats.add(seat2);

                Reservation reservation2 = Reservation.builder()
                        .reservationId(id++)
                        .seat(seat2)
                        .status(AssignmentStatus.NOT_BE_ASSIGNED)
                        .version(0L)
                        .build();
                createReservations.add(reservation2);

                Seat seat3 = Seat.builder()
                        .seatId(id)
                        .concert(createConcert3)
                        .seatNumber(j)
                        .status(TicketingStatus.NONE)
                        .build();
                createSeats.add(seat3);

                Reservation reservation3 = Reservation.builder()
                        .reservationId(id++)
                        .seat(seat3)
                        .status(AssignmentStatus.NOT_BE_ASSIGNED)
                        .version(0L)
                        .build();
                createReservations.add(reservation3);
            }

            this.createSeat(createSeats);
            this.createReservation(createReservations);
        }
    }

    private void createSeat(List<Seat> createSeats) {
        String query = """
            INSERT INTO seat(
                seat_id, concert_code, concert_date, seat_number, status
            ) VALUES (
                ?, ?, ?, ?, ?
            )
            """;

        jdbcTemplate.batchUpdate(query, createSeats, batchSize, (PreparedStatement ps, Seat seat) -> {
            ps.setLong(1, seat.getSeatId());
            ps.setString(2, seat.getConcert().getConcertPK().getConcertCode());
            ps.setTimestamp(3, Timestamp.valueOf(seat.getConcert().getConcertPK().getConcertDate()));
            ps.setInt(4, seat.getSeatNumber());
            ps.setString(5, seat.getStatus().toString());
        });
    }

    private void createReservation(List<Reservation> createReservations) {
        String query = """
            INSERT INTO reservation(
                reservation_id, seat_id, status, version
            ) VALUES (
                ?, ?, ?, ?
            )
            """;

        jdbcTemplate.batchUpdate(query, createReservations, batchSize, (PreparedStatement ps, Reservation reservation) -> {
            ps.setLong(1, reservation.getReservationId());
            ps.setLong(2, reservation.getSeat().getSeatId());
            ps.setString(3, reservation.getStatus().toString());
            ps.setLong(4, reservation.getVersion());
        });
    }
}
