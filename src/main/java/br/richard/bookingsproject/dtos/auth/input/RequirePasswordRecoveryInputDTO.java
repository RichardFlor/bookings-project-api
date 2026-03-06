package br.richard.bookingsproject.dtos.auth.input;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class RequirePasswordRecoveryInputDTO {
    @Email
    private String email;
}
