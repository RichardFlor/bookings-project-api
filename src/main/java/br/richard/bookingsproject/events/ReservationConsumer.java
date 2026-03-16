package br.richard.bookingsproject.events;

import br.richard.bookingsproject.dtos.events.input.ReservationCreatedEventInputDTO;
import br.richard.bookingsproject.dtos.events.input.ReservationRequestedEventInputDTO;
import br.richard.bookingsproject.entities.RentalType;
import br.richard.bookingsproject.entities.Reservation;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.repositories.rentaltype.RentalTypeJpaRepository;
import br.richard.bookingsproject.repositories.reservation.ReservationJpaRepository;
import br.richard.bookingsproject.repositories.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationConsumer {

    private final ReservationJpaRepository reservationJpaRepository;
    private final RentalTypeJpaRepository rentalTypeJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ReservationProducer reservationProducer;

    @Transactional
    @KafkaListener(
            topics = "reservation-requested",
            groupId = "reservation-group"
    )
    public void processReservationRequest(ReservationRequestedEventInputDTO event) {
        log.info("Processing reservation — user {} for {}",
                event.getUserId(), event.getReservationDate());

        try {
            var user = userJpaRepository.findById(event.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            br.richard.bookingsproject.entities.User.class));

            var rentalType = rentalTypeJpaRepository.findById(event.getRentalTypeId())
                    .orElseThrow(() -> new EntityNotFoundException(RentalType.class));

            if (reservationJpaRepository.existsByRentalTypeIdAndReservationDate(
                    event.getRentalTypeId(),
                    event.getReservationDate())) {
                log.warn("Reservation for {} of type {} cannot be processed — request discarded",
                        event.getReservationDate(), event.getRentalTypeId());
                return;
            }

            var reservation = Reservation.builder()
                    .user(user)
                    .rentalType(rentalType)
                    .reservationDate(event.getReservationDate())
                    .build();

            reservationJpaRepository.save(reservation);

            log.info("Reserva {} criada com sucesso para usuário {}",
                    reservation.getId(), event.getUserId());

            var confirmedEvent = ReservationCreatedEventInputDTO.builder()
                    .reservationId(reservation.getId())
                    .userId(reservation.getUser().getId())
                    .reservationDate(reservation.getReservationDate())
                    .status("CREATED")
                    .build();

            reservationProducer.sendReservationCreatedEvent(confirmedEvent);

        } catch (Exception e) {
            log.error("Erro ao processar pedido de reserva para usuário {}: {}",
                    event.getUserId(), e.getMessage());
            throw e;
        }
    }
}