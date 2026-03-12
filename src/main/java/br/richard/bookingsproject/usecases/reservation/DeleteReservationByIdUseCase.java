package br.richard.bookingsproject.usecases.reservation;

import br.richard.bookingsproject.entities.Reservation;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.exceptions.BusinessRuleException;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.repositories.reservation.ReservationJpaRepository;
import br.richard.bookingsproject.services.AuthenticationContextServiceImpl;
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
    private final AuthenticationContextServiceImpl authService;

    @Transactional
    public void execute(UUID reservationId) {

        var user = authService.getLoggedUser();

        var reservation = reservationJpaRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(Reservation.class));

        boolean isOwner = reservation.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRole().name().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new BusinessRuleException(ExceptionCode.FORBIDDEN_OPERATION);
        }

        log.info("Deleting reservation {} by user {}", reservation.getId(), user.getId());

        reservationJpaRepository.delete(reservation);
    }
}