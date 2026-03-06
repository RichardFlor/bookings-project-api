package br.richard.bookingsproject.dtos.auth.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ValidatePasswordRecoveryCodeInputDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String code;
}
