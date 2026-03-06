package br.richard.bookingsproject.dtos.user.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordInputDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String code;

    @NotBlank
    private String password;
}
