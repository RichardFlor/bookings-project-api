package br.richard.bookingsproject.rest.controllers;

import br.richard.bookingsproject.dtos.commons.pagination.output.PaginationOutputDTO;
import br.richard.bookingsproject.dtos.reservation.input.CreateReservationInputDTO;
import br.richard.bookingsproject.dtos.reservation.input.FindReservationsByCurrentUserFiltersInputDTO;
import br.richard.bookingsproject.dtos.reservation.input.UpdateReservationInputDTO;
import br.richard.bookingsproject.dtos.reservation.output.FindReservationsByCurrentUserOutputDTO;
import br.richard.bookingsproject.rest.specs.ReservationControllerSpecs;
import br.richard.bookingsproject.usecases.reservation.CreateReservationUseCase;
import br.richard.bookingsproject.usecases.reservation.DeleteReservationByIdUseCase;
import br.richard.bookingsproject.usecases.reservation.FindReservationsByCurrentUserUseCase;
import br.richard.bookingsproject.usecases.reservation.UpdateReservationByCurrentUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController implements ReservationControllerSpecs {

    private final CreateReservationUseCase createReservationUseCase;
    private final DeleteReservationByIdUseCase deleteReservationByIdUseCase;
    private final FindReservationsByCurrentUserUseCase findReservationsByCurrentUserUseCase;
    private final UpdateReservationByCurrentUserUseCase updateReservationByCurrentUserUseCase;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateReservationInputDTO request) {
        createReservationUseCase.execute(request);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{id}")
    public void deleted(@PathVariable UUID id) {
        this.deleteReservationByIdUseCase.execute(id);
    }

    @GetMapping("/my-reservations")
    public PaginationOutputDTO<FindReservationsByCurrentUserOutputDTO> listMyReservations(
            @ParameterObject
            @ModelAttribute
            FindReservationsByCurrentUserFiltersInputDTO request
    ) {
        return findReservationsByCurrentUserUseCase.execute(request);
    }

    @PatchMapping("/{id}")
    public void updateReservation(@PathVariable UUID id, @RequestBody @Valid UpdateReservationInputDTO request) {
        updateReservationByCurrentUserUseCase.execute(id, request);
    }
}