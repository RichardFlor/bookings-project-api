package br.richard.bookingsproject.dtos.reservation.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindReservationsByCurrentUserOutputDTO {
    @Schema(example = "b8a8c3f6-0f2d-4b7b-9b1e-2d3b9c9a7f11")
    private UUID id;

    @Schema(example = "Beach House")
    private String rentalTypeName;

    @Schema(example = "2026-02-20")
    private LocalDate reservationDate;

    @Schema(example = "2026-03-10T10:15:30")
    private LocalDateTime createdAt;
}
