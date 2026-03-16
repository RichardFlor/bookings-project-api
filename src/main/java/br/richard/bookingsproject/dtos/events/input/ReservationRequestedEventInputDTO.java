package br.richard.bookingsproject.dtos.events.input;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequestedEventInputDTO {
    private UUID userId;
    private UUID rentalTypeId;
    private LocalDate reservationDate;
}
