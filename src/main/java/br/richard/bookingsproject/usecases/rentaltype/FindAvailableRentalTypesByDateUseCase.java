package br.richard.bookingsproject.usecases.rentaltype;

import br.richard.bookingsproject.dtos.commons.pagination.input.PaginationInputDTO;
import br.richard.bookingsproject.dtos.commons.pagination.output.PaginationOutputDTO;
import br.richard.bookingsproject.dtos.rentaltype.output.RentalTypeOutputDTO;
import br.richard.bookingsproject.mappers.rentaltype.RentalTypeStructMapper;
import br.richard.bookingsproject.repositories.rentaltype.RentalTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FindAvailableRentalTypesByDateUseCase {

    private final RentalTypeJpaRepository repository;
    private final RentalTypeStructMapper mapper;

    public PaginationOutputDTO<RentalTypeOutputDTO> execute(
            LocalDate date,
            PaginationInputDTO pagination
    ) {

        var pageable = PageRequest.of(
                pagination.getPage(),
                pagination.getLimit()
        );

        var page = repository.findAvailableByDate(date, pageable);

        var content = mapper.toRentalTypeOutputDTO(
                page.getContent()
        );

        return new PaginationOutputDTO<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}