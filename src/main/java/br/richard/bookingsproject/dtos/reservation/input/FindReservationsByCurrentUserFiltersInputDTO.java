package br.richard.bookingsproject.dtos.reservation.input;

import br.richard.bookingsproject.dtos.commons.pagination.input.PaginationInputDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FindReservationsByCurrentUserFiltersInputDTO extends PaginationInputDTO {

    private LocalDate reservationDate;

}