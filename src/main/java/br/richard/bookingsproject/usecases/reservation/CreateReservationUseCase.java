package br.richard.bookingsproject.usecases.reservation;

import br.richard.bookingsproject.dtos.reservation.input.CreateReservationInputDTO;
import br.richard.bookingsproject.entities.RentalType;
import br.richard.bookingsproject.entities.Reservation;
import br.richard.bookingsproject.entities.User;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.exceptions.BusinessRuleException;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.repositories.reservation.ReservationJpaRepository;
import br.richard.bookingsproject.repositories.rentaltype.RentalTypeJpaRepository;
import br.richard.bookingsproject.services.AuthenticationContextServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateReservationUseCase {

    private final ReservationJpaRepository reservationJpaRepository;
    private final RentalTypeJpaRepository rentalTypeJpaRepository;
    private final AuthenticationContextServiceImpl authService;

    @Transactional
    public void execute(CreateReservationInputDTO input) {

        var user = authService.getLoggedUser();

        var rentalType = rentalTypeJpaRepository.findById(input.getRentalTypeId())
                .orElseThrow(() -> new EntityNotFoundException(RentalType.class));

        if (reservationJpaRepository.existsByRentalTypeIdAndReservationDate(
                input.getRentalTypeId(),
                input.getReservationDate())) {

            throw new BusinessRuleException(ExceptionCode.NOT_AVAILABLE_DATE_EXCEPTION);
        }

        var reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRentalType(rentalType);
        reservation.setReservationDate(input.getReservationDate());

        log.info("Creating reservation for logged user {}", user.getId());

        reservationJpaRepository.save(reservation);
    }
}