package br.richard.bookingsproject.usecases.reservation;

import br.richard.bookingsproject.dtos.reservation.input.UpdateReservationInputDTO;
import br.richard.bookingsproject.entities.RentalType;
import br.richard.bookingsproject.entities.Reservation;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.exceptions.BusinessRuleException;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.repositories.rentaltype.RentalTypeJpaRepository;
import br.richard.bookingsproject.repositories.reservation.ReservationJpaRepository;
import br.richard.bookingsproject.services.AuthenticationContextServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateReservationByCurrentUserUseCase {
    private final ReservationJpaRepository reservationJpaRepository;
    private final RentalTypeJpaRepository rentalTypeJpaRepository;
    private final AuthenticationContextServiceImpl authService;

    @Transactional
    public void execute(UUID reservationId, UpdateReservationInputDTO input) {

        var user = authService.getLoggedUser();

        var reservation = reservationJpaRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(Reservation.class));

        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new BusinessRuleException(ExceptionCode.FORBIDDEN_OPERATION);
        }

        if (input.getReservationDate() == null ||
                input.getReservationDate().isBefore(LocalDate.now())) {

            throw new BusinessRuleException(ExceptionCode.INVALID_RESERVATION_DATE);
        }

        var rentalType = rentalTypeJpaRepository.findById(input.getRentalTypeId())
                .orElseThrow(() -> new EntityNotFoundException(RentalType.class));

        boolean conflict = reservationJpaRepository
                .existsByRentalTypeIdAndReservationDateAndIdNot(
                        rentalType.getId(),
                        input.getReservationDate(),
                        reservation.getId()
                );

        if (conflict) {
            throw new BusinessRuleException(ExceptionCode.NOT_AVAILABLE_DATE_EXCEPTION);
        }

        reservation.setRentalType(rentalType);
        reservation.setReservationDate(input.getReservationDate());

        reservationJpaRepository.save(reservation);
    }
}