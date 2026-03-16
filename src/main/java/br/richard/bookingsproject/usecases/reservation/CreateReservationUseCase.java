package br.richard.bookingsproject.usecases.reservation;

import br.richard.bookingsproject.dtos.events.input.ReservationRequestedEventInputDTO;
import br.richard.bookingsproject.dtos.reservation.input.CreateReservationInputDTO;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.exceptions.BusinessRuleException;
import br.richard.bookingsproject.services.AuthenticationContextServiceImpl;
import br.richard.bookingsproject.events.ReservationProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateReservationUseCase {

    private final AuthenticationContextServiceImpl authService;
    private final ReservationProducer reservationProducer;

    public void execute(CreateReservationInputDTO input) {

        var user = authService.getLoggedUser();

        if (input.getReservationDate().isBefore(LocalDate.now())) {
            throw new BusinessRuleException(ExceptionCode.INVALID_RESERVATION_DATE);
        }

        var event = ReservationRequestedEventInputDTO.builder()
                .userId(user.getId())
                .rentalTypeId(input.getRentalTypeId())
                .reservationDate(input.getReservationDate())
                .build();

        log.info("Publishing reservation order for user {} on {}",
                user.getId(), input.getReservationDate());

        reservationProducer.sendReservationRequestedEvent(event);
    }
}