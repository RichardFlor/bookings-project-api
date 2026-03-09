package br.richard.bookingsproject.usecases.rentaltype;

import br.richard.bookingsproject.dtos.rentaltype.output.RentalTypeOutputDTO;
import br.richard.bookingsproject.mappers.rentaltype.RentalTypeStructMapper;
import br.richard.bookingsproject.repositories.rentaltype.RentalTypeJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindAllRentalTypesUseCase {
    private final RentalTypeJpaRepository rentalTypeJpaRepository;
    private final RentalTypeStructMapper rentalTypeStructMapper;

    @Transactional
    public Set<RentalTypeOutputDTO> execute() {
        return new HashSet<>(rentalTypeStructMapper.toRentalTypeOutputDTO(
                rentalTypeJpaRepository.findAll()
        ));
    }
}
