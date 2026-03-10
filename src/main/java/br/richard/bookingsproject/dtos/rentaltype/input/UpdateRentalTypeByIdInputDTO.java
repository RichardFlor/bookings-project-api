package br.richard.bookingsproject.dtos.rentaltype.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalTypeByIdInputDTO {
    private String name;

    private String description;
}
