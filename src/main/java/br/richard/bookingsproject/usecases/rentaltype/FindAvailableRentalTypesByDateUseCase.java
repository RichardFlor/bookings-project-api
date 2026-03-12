package br.richard.bookingsproject.usecases.rentaltype;

import br.richard.bookingsproject.dtos.rentaltype.output.RentalTypeOutputDTO;
import br.richard.bookingsproject.mappers.rentaltype.RentalTypeStructMapper;
import br.richard.bookingsproject.repositories.rentaltype.RentalTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAvailableRentalTypesByDateUseCase {

    private final RentalTypeJpaRepository repository;
    private final RentalTypeStructMapper mapper;

    public List<RentalTypeOutputDTO> execute(LocalDate date) {

        var rentalTypes = repository.findAvailableByDate(date);

        return rentalTypes.stream()
                .map(mapper::toRentalTypeOutputDTO)
                .toList();
    }
}