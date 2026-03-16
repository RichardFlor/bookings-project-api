package br.richard.bookingsproject.services;

import br.richard.bookingsproject.dtos.events.ReservationCreatedEventInputDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationConsumer {

    // TODO: Implement email service
    // TODO: private final EmailService emailService;

    @KafkaListener(
            topics = "reservation-created",
            groupId = "reservation-group"
    )
    public void consumeReservationCreatedEvent(ReservationCreatedEventInputDTO event) {
        log.info("Processing reservation {} for the user {}",
                event.getReservationId(), event.getUserId());

        try {
            // TODO: Implement email service
            // TODO: emailService.sendReservationConfirmation(event);
            log.info("Reservation {} processed successfully", event.getReservationId());
        } catch (Exception e) {
            log.error("Error processing reservation {}: {}", event.getReservationId(), e.getMessage());
            throw e;
        }
    }
}