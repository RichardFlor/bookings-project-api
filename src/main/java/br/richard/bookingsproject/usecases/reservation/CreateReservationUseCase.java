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
import br.richard.bookingsproject.repositories.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateReservationUseCase {

    private final ReservationJpaRepository reservationJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final RentalTypeJpaRepository rentalTypeJpaRepository;

    @Transactional
    public void execute(CreateReservationInputDTO input) {

        var user = userJpaRepository.findById(input.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        var rentalType = rentalTypeJpaRepository.findById(input.getRentalTypeId())
                .orElseThrow(() -> new EntityNotFoundException(RentalType.class));

        if (reservationJpaRepository.existsByRentalTypeIdAndReservationDate(
                input.getRentalTypeId(),
                input.getReservationDate())) {
            //throw new RuntimeException("This rental type is already reserved for this date.");
            throw new BusinessRuleException(ExceptionCode.NOT_AVAILABLE_DATE_EXCEPTION);
        }

        var reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRentalType(rentalType);
        reservation.setReservationDate(input.getReservationDate());

        log.info("Creating reservation for user {}", input.getUserId());

        reservationJpaRepository.save(reservation);
    }
}