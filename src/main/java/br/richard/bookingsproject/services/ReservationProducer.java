package br.richard.bookingsproject.services;

import br.richard.bookingsproject.dtos.events.ReservationCreatedEventInputDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "reservation-created";

    public void sendReservationCreatedEvent(ReservationCreatedEventInputDTO event) {
        kafkaTemplate.send(TOPIC, event.getReservationId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failure to publish reservation event {}: {}",
                                event.getReservationId(), ex.getMessage());
                    } else {
                        log.info("Event published — reservation {} | partition {} | offset {}",
                                event.getReservationId(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}