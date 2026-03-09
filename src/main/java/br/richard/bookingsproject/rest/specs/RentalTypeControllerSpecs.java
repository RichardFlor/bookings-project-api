package br.richard.bookingsproject.rest.specs;

import br.richard.bookingsproject.dtos.rentaltype.input.CreateRentalTypeInputDTO;
import br.richard.bookingsproject.dtos.rentaltype.output.RentalTypeOutputDTO;
import br.richard.bookingsproject.rest.specs.commons.response.error.ApiResponseBadRequest;
import br.richard.bookingsproject.rest.specs.commons.response.error.ApiResponseDuplicatedResource;
import br.richard.bookingsproject.rest.specs.commons.response.error.ApiResponseInternalServerError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;
import java.util.UUID;

@ApiResponseInternalServerError
@Tag(name = "3. Rental Type", description = "Rental Type operations")
public interface RentalTypeControllerSpecs {
    @Operation(summary = "Create rental type", description = "Required roles: `ADMIN`")
    @ApiResponseBadRequest
    @ApiResponseDuplicatedResource
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "jwt")
    void create(@RequestBody @Valid CreateRentalTypeInputDTO request);

    @Operation(summary = "Delete the rental type",  description = "Required roles: `ADMIN`")
    @ApiResponse(responseCode = "204", description = "No content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "jwt")
    void deleted(@PathVariable UUID id);

    @Operation(summary = "List rental types")
    @ApiResponse(responseCode = "200", description = "Ok", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = RentalTypeOutputDTO.class)))
    })
    @SecurityRequirement(name="jwt")
    Set<RentalTypeOutputDTO> findAll();
}
