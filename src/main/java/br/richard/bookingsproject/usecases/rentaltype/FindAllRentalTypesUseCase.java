package br.richard.bookingsproject.usecases.rentaltype;

import br.richard.bookingsproject.dtos.commons.pagination.output.PaginationOutputDTO;
import br.richard.bookingsproject.dtos.rentaltype.input.FindRentalTypesByFiltersInputDTO;
import br.richard.bookingsproject.dtos.rentaltype.output.RentalTypeOutputDTO;
import br.richard.bookingsproject.mappers.rentaltype.RentalTypeStructMapper;
import br.richard.bookingsproject.repositories.rentaltype.RentalTypeRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindAllRentalTypesUseCase {

    private final RentalTypeRepositoryImpl rentalTypeRepository;
    private final RentalTypeStructMapper rentalTypeStructMapper;

    @Transactional
    public PaginationOutputDTO<RentalTypeOutputDTO> execute(FindRentalTypesByFiltersInputDTO input) {

        var page = rentalTypeRepository.findAll(input);

        var content = rentalTypeStructMapper.toRentalTypeOutputDTO(
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