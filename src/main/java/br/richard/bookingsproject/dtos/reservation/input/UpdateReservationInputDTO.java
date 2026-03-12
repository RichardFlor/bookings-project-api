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
public class UpdateReservationInputDTO {

    @Schema(example = "9f1c2d54-8c6e-4e33-a1f7-2fbb3d9e8c44")
    private UUID rentalTypeId;

    @Schema(example = "2026-02-20")
    private LocalDate reservationDate;

}
