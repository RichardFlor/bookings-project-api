package br.richard.bookingsproject.dtos.rentaltype.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RentalTypeOutputDTO {
    private UUID id;

    @Schema(example = "Reserva restaurante")
    private String name;

    private String description;


    //private BoardStatus boardStatus;
}
