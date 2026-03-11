package br.richard.bookingsproject.usecases.reservation;

import br.richard.bookingsproject.entities.Reservation;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.repositories.reservation.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteReservationByIdUseCase {
    private final ReservationJpaRepository reservationJpaRepository;

    @Transactional
    public void execute(UUID reservationId) {

        var reservation = reservationJpaRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(Reservation.class));

        log.info("Deleting reservation: {}", reservation.getId());
        reservationJpaRepository.delete(reservation);
    }
}