package br.richard.bookingsproject.dtos.auth.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInputDTO {
    @Email
    @Schema(example = "example@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    private String password;
}
