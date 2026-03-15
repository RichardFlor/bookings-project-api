package br.richard.bookingsproject.usecases.reservation;

import br.richard.bookingsproject.dtos.events.ReservationCreatedEventInputDTO;
import br.richard.bookingsproject.dtos.reservation.input.CreateReservationInputDTO;
import br.richard.bookingsproject.entities.RentalType;
import br.richard.bookingsproject.entities.Reservation;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.exceptions.BusinessRuleException;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.repositories.reservation.ReservationJpaRepository;
import br.richard.bookingsproject.repositories.rentaltype.RentalTypeJpaRepository;
import br.richard.bookingsproject.services.AuthenticationContextServiceImpl;
import br.richard.bookingsproject.services.ReservationProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateReservationUseCase {

    private final ReservationJpaRepository reservationJpaRepository;
    private final RentalTypeJpaRepository rentalTypeJpaRepository;
    private final AuthenticationContextServiceImpl authService;
    private final ReservationProducer reservationProducer;

    @Transactional
    public void execute(CreateReservationInputDTO input) {

        var user = authService.getLoggedUser();

        if (input.getReservationDate().isBefore(LocalDate.now())) {
            throw new BusinessRuleException(ExceptionCode.INVALID_RESERVATION_DATE);
        }

        var rentalType = rentalTypeJpaRepository.findById(input.getRentalTypeId())
                .orElseThrow(() -> new EntityNotFoundException(RentalType.class));

        if (reservationJpaRepository.existsByRentalTypeIdAndReservationDate(
                input.getRentalTypeId(),
                input.getReservationDate())) {

            throw new BusinessRuleException(ExceptionCode.NOT_AVAILABLE_DATE_EXCEPTION);
        }

        var reservation = Reservation.builder()
                .user(user)
                .rentalType(rentalType)
                .reservationDate(input.getReservationDate())
                .build();

        log.info("Creating reservation for logged user {}", user.getId());

        reservationJpaRepository.save(reservation);

        ReservationCreatedEventInputDTO event =
                ReservationCreatedEventInputDTO.builder()
                        .reservationId(reservation.getId())
                        .userId(reservation.getUser().getId())
                        .reservationDate(reservation.getReservationDate())
                        .status("CREATED")
                        .build();

        reservationProducer.sendReservationCreatedEvent(event);
    }
}