package br.richard.bookingsproject.dtos.events;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationCreatedEventInputDTO {
    private UUID reservationId;

    private UUID userId;

    private LocalDate reservationDate;

    private String status;
}
