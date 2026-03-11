package br.richard.bookingsproject.dtos.reservation.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationInputDTO {
    @Schema(
            description = "ID do usuário que está fazendo a reserva",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    private UUID userId;

    @Schema(
            description = "ID do tipo de aluguel",
            example = "9f1c2d54-8c6e-4e33-a1f7-2fbb3d9e8c44"
    )
    private UUID rentalTypeId;

    @Schema(
            description = "Data da reserva",
            type = "string",
            format = "date",
            example = "2026-03-20"
    )
    private LocalDate reservationDate;
}
