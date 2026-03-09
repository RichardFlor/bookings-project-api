package br.richard.bookingsproject.dtos.rentaltype.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalTypeInputDTO {
    @NotBlank
    @Schema(example = "string", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 120)
    private String name;

    @Size(max = 500)
    private String description;

}
