package br.richard.bookingsproject.rest.controllers;

import br.richard.bookingsproject.dtos.commons.pagination.output.PaginationOutputDTO;
import br.richard.bookingsproject.dtos.rentaltype.input.CreateRentalTypeInputDTO;
import br.richard.bookingsproject.dtos.rentaltype.input.FindRentalTypesByFiltersInputDTO;
import br.richard.bookingsproject.dtos.rentaltype.input.UpdateRentalTypeByIdInputDTO;
import br.richard.bookingsproject.dtos.rentaltype.output.RentalTypeOutputDTO;
import br.richard.bookingsproject.rest.specs.RentalTypeControllerSpecs;
import br.richard.bookingsproject.usecases.rentaltype.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rental-types")
@RequiredArgsConstructor
public class RentalTypeController implements RentalTypeControllerSpecs {
    private final CreateRentalTypeUseCase createRentalTypeUseCase;
    private final DeleteRentalTypeByIdUseCase deleteRentalTypeByIdUseCase;
    private final FindAllRentalTypesUseCase findAllRentalTypesUseCase;
    private final UpdateRentalTypeByIdUseCase updateRentalTypeByIdUseCase;
    private final FindAvailableRentalTypesByDateUseCase findAvailableRentalTypesByDateUseCase;

    @PostMapping
    public void create(@RequestBody @Valid CreateRentalTypeInputDTO request){
        createRentalTypeUseCase.execute(request);
    }

    @DeleteMapping("/{id}")
    public void deleted(@PathVariable UUID id){ this.deleteRentalTypeByIdUseCase.execute(id);}

    @GetMapping()
    public PaginationOutputDTO<RentalTypeOutputDTO> list(
            @ParameterObject
            @ModelAttribute
            @Valid
            FindRentalTypesByFiltersInputDTO request
    ) {
        return findAllRentalTypesUseCase.execute(request);
    }

    @PatchMapping("/{id}")
    public void update(@RequestBody @Valid UpdateRentalTypeByIdInputDTO request, @PathVariable UUID id){
        this.updateRentalTypeByIdUseCase.execute(request, id);
    }

    @GetMapping("/available")
    public List<RentalTypeOutputDTO> findAvailableByDate(@RequestParam LocalDate date) {
        return findAvailableRentalTypesByDateUseCase.execute(date);
    }
}
