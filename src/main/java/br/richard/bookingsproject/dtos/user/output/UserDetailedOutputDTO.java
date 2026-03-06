package br.richard.bookingsproject.dtos.user.output;

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

    @Schema(example = "Jorge")
    private String name;

    @Schema(example = "jorge@gmail")
    private String email;

    private LocalDateTime disabledAt;
}
