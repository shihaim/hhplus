package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;
import com.example.ticketing.domain.concert.repository.ConcertSeatReaderRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ConcertSeatReader {

    private final ConcertSeatReaderRepository readerRepository;

    public ConcertSeatReader(ConcertSeatReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    /**
     * 해당 날짜의 예매 가능한 좌석 조회
     */
    public List<Seat> findAvailableSeats(String concertCode, LocalDateTime concertDate) {
        return readerRepository.findAllByCodeAndDate(concertCode, concertDate);
    }

    /**
     * 예매가 되지 않은 좌석 조회
     */
    public Seat findNotCompletedSeat(String concertCode, LocalDateTime concertDate, int seatNumber) {
        return readerRepository.findByCodeAndDateAndStatus(concertCode, concertDate, seatNumber, TicketingStatus.NONE)
                .orElseThrow(() -> new NoSuchElementException("이미 예매가 완료된 좌석입니다."));
    }
}
