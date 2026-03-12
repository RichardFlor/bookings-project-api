package br.richard.bookingsproject.rest.specs;

import br.richard.bookingsproject.dtos.commons.pagination.output.PaginationOutputDTO;
import br.richard.bookingsproject.dtos.reservation.input.CreateReservationInputDTO;
import br.richard.bookingsproject.dtos.reservation.input.FindReservationsByCurrentUserFiltersInputDTO;
import br.richard.bookingsproject.dtos.reservation.input.UpdateReservationInputDTO;
import br.richard.bookingsproject.dtos.reservation.output.FindReservationsByCurrentUserOutputDTO;
import br.richard.bookingsproject.rest.specs.commons.response.error.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;
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

    @Operation(summary = "Delete the reservation",  description = "Required roles: `ADMIN`, `CUSTOMER`")
    @ApiResponse(responseCode = "204", description = "No content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "jwt")
    void deleted(@PathVariable UUID id);

    @Operation(summary = "List reservations of the logged user", description = "Returns the reservations made by the authenticated user. Required roles: `CUSTOMER`")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = PaginationOutputDTO.class)))
    })
    @SecurityRequirement(name = "jwt")
    PaginationOutputDTO<FindReservationsByCurrentUserOutputDTO> listMyReservations(
            @ParameterObject
            @ModelAttribute
            FindReservationsByCurrentUserFiltersInputDTO request
    );

    @Operation(summary = "Update reservation of logged user",  description = "Required roles: `CUSTOMER`")
    @ApiResponseNotFound
    @ApiResponseBusinessRuleException
    @ApiResponse(
            responseCode = "403",
            description = "The user is authenticated but does not have permission to perform this action."
    )
    @ApiResponse(responseCode = "204", description = "No Content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "jwt")
    void updateReservation(UUID id, UpdateReservationInputDTO request);
}
