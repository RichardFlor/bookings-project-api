package br.richard.bookingsproject.rest.controllers;

import br.richard.bookingsproject.dtos.reservation.input.CreateReservationInputDTO;
import br.richard.bookingsproject.rest.specs.ReservationControllerSpecs;
import br.richard.bookingsproject.usecases.reservation.CreateReservationUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController implements ReservationControllerSpecs {
    private final CreateReservationUseCase createReservationUseCase;

    @PostMapping
    public void create(@RequestBody @Valid CreateReservationInputDTO request){ createReservationUseCase.execute(request);}
}
