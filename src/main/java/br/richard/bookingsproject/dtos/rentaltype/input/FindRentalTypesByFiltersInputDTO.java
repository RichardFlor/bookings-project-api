package br.richard.bookingsproject.dtos.rentaltype.input;

import br.richard.bookingsproject.dtos.commons.pagination.input.PaginationInputDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindRentalTypesByFiltersInputDTO extends PaginationInputDTO {
    private String name;

    private String description;

}