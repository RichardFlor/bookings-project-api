package br.richard.bookingsproject.rest.controllers;

import br.richard.bookingsproject.dtos.reservation.input.CreateReservationInputDTO;
import br.richard.bookingsproject.rest.specs.ReservationControllerSpecs;
import br.richard.bookingsproject.usecases.reservation.CreateReservationUseCase;
import br.richard.bookingsproject.usecases.reservation.DeleteReservationByIdUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController implements ReservationControllerSpecs {
    private final CreateReservationUseCase createReservationUseCase;
    private final DeleteReservationByIdUseCase deleteReservationByIdUseCase;

    @PostMapping
    public void create(@RequestBody @Valid CreateReservationInputDTO request){ createReservationUseCase.execute(request);}

    @DeleteMapping("/{id}")
    public void deleted(@PathVariable UUID id){ this.deleteReservationByIdUseCase.execute(id);}
}
