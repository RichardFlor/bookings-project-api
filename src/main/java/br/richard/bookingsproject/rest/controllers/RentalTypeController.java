package br.richard.bookingsproject.rest.controllers;

import br.richard.bookingsproject.dtos.rentaltype.input.CreateRentalTypeInputDTO;
import br.richard.bookingsproject.rest.specs.RentalTypeControllerSpecs;
import br.richard.bookingsproject.usecases.rentaltype.CreateRentalTypeUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rental-types")
@RequiredArgsConstructor
public class RentalTypeController implements RentalTypeControllerSpecs {
    private final CreateRentalTypeUseCase createRentalTypeUseCase;

    @PostMapping
    public void create(@RequestBody @Valid CreateRentalTypeInputDTO request){
        createRentalTypeUseCase.execute(request);
    }
}
