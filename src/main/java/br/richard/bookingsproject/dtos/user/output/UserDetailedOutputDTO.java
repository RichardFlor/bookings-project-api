package br.richard.bookingsproject.dtos.user.output;

import br.richard.bookingsproject.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailedOutputDTO {

    private UUID id;

    @Schema(example = "Example")
    private String name;

    @Schema(example = "example@gmail.com")
    private String email;

    @Schema(example = "GUEST")
    private UserRole role;

    private LocalDateTime emailValidatedAt;

    private LocalDateTime createdAt;
}