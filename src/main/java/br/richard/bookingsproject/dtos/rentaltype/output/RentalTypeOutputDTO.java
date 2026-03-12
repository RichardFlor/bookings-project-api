package br.richard.bookingsproject.dtos.rentaltype.output;

import io.swagger.v3.oas.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class RentalTypeOutputDTO {
    private UUID id;

    @Parameter(description = "McDonuts")
    private String name;

    @Parameter(description = "McDonuts is a shop that sells donuts.")
    private String description;
}
