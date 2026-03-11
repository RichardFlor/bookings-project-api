package br.richard.bookingsproject.rest.specs;

import br.richard.bookingsproject.dtos.reservation.input.CreateReservationInputDTO;
import br.richard.bookingsproject.rest.specs.commons.response.error.ApiResponseBadRequest;
import br.richard.bookingsproject.rest.specs.commons.response.error.ApiResponseDuplicatedResource;
import br.richard.bookingsproject.rest.specs.commons.response.error.ApiResponseInternalServerError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ApiResponseInternalServerError
@Tag(name = "4. Reservation", description = "Reservation operations")
public interface ReservationControllerSpecs {
    @Operation(summary = "Create reservation")
    @ApiResponseBadRequest
    @ApiResponseDuplicatedResource
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "jwt")
    void create(@RequestBody @Valid CreateReservationInputDTO request);

    @Operation(summary = "Delete the reservation",  description = "Required roles: `ADMIN`")
    @ApiResponse(responseCode = "204", description = "No content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "jwt")
    void deleted(@PathVariable UUID id);
}
