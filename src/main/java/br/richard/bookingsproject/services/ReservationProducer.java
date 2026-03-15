package br.richard.bookingsproject.services;


import br.richard.bookingsproject.dtos.events.ReservationCreatedEventInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "reservation-created";

    public void sendReservationCreatedEvent(ReservationCreatedEventInputDTO event) {

        kafkaTemplate.send(TOPIC, event);

    }
}