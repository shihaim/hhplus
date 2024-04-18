package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.ConcertPK;
import com.example.ticketing.domain.concert.entity.Seat;
import com.example.ticketing.domain.concert.entity.TicketingStatus;
import com.example.ticketing.domain.concert.repository.SeatReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ConcertSeatReader {

    private final SeatReaderRepository readerRepository;

    /**
     * 해당 날짜의 예매 가능한 좌석 조회
     */
    @Transactional(readOnly = true)
    public List<Seat> findAvailableSeats(String concertCode, LocalDateTime concertDate) {
        return readerRepository.findAllByConcertPk(ConcertPK.of(concertCode, concertDate));
    }

    /**
     * 예매가 되지 않은 좌석 조회
     */
    public Seat findNotCompletedSeat(String concertCode, LocalDateTime concertDate, int seatNumber) {
        return readerRepository.findByConcertPKAndSeatNumberAndStatus(ConcertPK.of(concertCode, concertDate), seatNumber, TicketingStatus.NONE)
                .orElseThrow(() -> new NoSuchElementException("이미 예매가 완료된 좌석입니다."));
    }
}
