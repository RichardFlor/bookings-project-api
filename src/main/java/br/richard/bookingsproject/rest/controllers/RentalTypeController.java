package br.richard.bookingsproject.rest.controllers;

import br.richard.bookingsproject.dtos.rentaltype.input.CreateRentalTypeInputDTO;
import br.richard.bookingsproject.dtos.rentaltype.input.UpdateRentalTypeByIdInputDTO;
import br.richard.bookingsproject.dtos.rentaltype.output.RentalTypeOutputDTO;
import br.richard.bookingsproject.rest.specs.RentalTypeControllerSpecs;
import br.richard.bookingsproject.usecases.rentaltype.CreateRentalTypeUseCase;
import br.richard.bookingsproject.usecases.rentaltype.DeleteRentalTypeByIdUseCase;
import br.richard.bookingsproject.usecases.rentaltype.FindAllRentalTypesUseCase;
import br.richard.bookingsproject.usecases.rentaltype.UpdateRentalTypeByIdUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/rental-types")
@RequiredArgsConstructor
public class RentalTypeController implements RentalTypeControllerSpecs {
    private final CreateRentalTypeUseCase createRentalTypeUseCase;
    private final DeleteRentalTypeByIdUseCase deleteRentalTypeByIdUseCase;
    private final FindAllRentalTypesUseCase findAllRentalTypesUseCase;
    private final UpdateRentalTypeByIdUseCase updateRentalTypeByIdUseCase;

    @PostMapping
    public void create(@RequestBody @Valid CreateRentalTypeInputDTO request){
        createRentalTypeUseCase.execute(request);
    }

    @DeleteMapping("/{id}")
    public void deleted(@PathVariable UUID id){ this.deleteRentalTypeByIdUseCase.execute(id);}

    @GetMapping()
    public Set<RentalTypeOutputDTO> findAll(){return findAllRentalTypesUseCase.execute();}

    @PatchMapping("/{id}")
    public void update(@RequestBody @Valid UpdateRentalTypeByIdInputDTO request, @PathVariable UUID id){
        this.updateRentalTypeByIdUseCase.execute(request, id);
    }
}
