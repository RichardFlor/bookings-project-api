package br.richard.bookingsproject.events;


import br.richard.bookingsproject.dtos.events.input.ReservationCreatedEventInputDTO;
import br.richard.bookingsproject.dtos.events.input.ReservationRequestedEventInputDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_REQUESTED  = "reservation-requested";
    private static final String TOPIC_CREATED    = "reservation-created";

    public void sendReservationRequestedEvent(ReservationRequestedEventInputDTO event) {
        kafkaTemplate.send(TOPIC_REQUESTED, event.getUserId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Reservation request publishing failed for user {}: {}",
                                event.getUserId(), ex.getMessage());
                    } else {
                        log.info("Reservation successfully published for user {} | partition {} | offset {}",
                                event.getUserId(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }

    public void sendReservationCreatedEvent(ReservationCreatedEventInputDTO event) {
        kafkaTemplate.send(TOPIC_CREATED, event.getReservationId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send reservation confirmation {}: {}",
                                event.getReservationId(), ex.getMessage());
                    } else {
                        log.info("Reservation successfully confirmed — id {} | partition {} | offset {}",
                                event.getReservationId(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}