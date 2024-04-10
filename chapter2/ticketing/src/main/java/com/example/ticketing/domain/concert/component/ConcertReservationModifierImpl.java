package com.example.ticketing.domain.concert.component;

import com.example.ticketing.domain.concert.entity.AssignmentStatus;
import com.example.ticketing.domain.concert.entity.Reservation;
import com.example.ticketing.domain.concert.repository.ConcertReservationReaderRepository;
import jakarta.persistence.OptimisticLockException;

import java.util.NoSuchElementException;

public class ConcertReservationModifierImpl implements ConcertReservationModifier {

    private final ConcertReservationReaderRepository readerRepository;

    public ConcertReservationModifierImpl(ConcertReservationReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public Reservation reserveSeat(Long seatId, String userUUID, int token) {
        Reservation findReservation = readerRepository.findNotAssignedBySeatId(seatId, AssignmentStatus.NOT_BE_ASSIGNED)
                .orElseThrow(() -> new NoSuchElementException("배정할 수 없는 좌석입니다."));

        int version = findReservation.getVersion();

        findReservation.assignment(userUUID, token);

        if (version != readerRepository.findVersion(findReservation.getReservationId())) {
            throw new OptimisticLockException("이미 임시 배정된 좌석입니다.");
        }

        findReservation.increaseVersion();

        return findReservation;
    }
}
