package br.richard.bookingsproject.services;

import br.richard.bookingsproject.dtos.events.ReservationCreatedEventInputDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReservationConsumer {

    @KafkaListener(
            topics = "reservation-created",
            groupId = "reservation-group"
    )
    public void consumeReservationCreatedEvent(ReservationCreatedEventInputDTO event) {

        log.info("Reservation event received: {}", event);

    }
}
