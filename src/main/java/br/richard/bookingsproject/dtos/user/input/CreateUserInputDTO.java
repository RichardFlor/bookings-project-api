package br.richard.bookingsproject.dtos.user.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserInputDTO {

    @NotBlank
    @Schema(example = "Example", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Email
    @NotNull
    @Schema(example = "example@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Schema(example = "string", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}