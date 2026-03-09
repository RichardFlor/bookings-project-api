package br.richard.bookingsproject.usecases.rentaltype;

import br.richard.bookingsproject.config.annotations.UseCase;
import br.richard.bookingsproject.dtos.rentaltype.input.CreateRentalTypeInputDTO;
import br.richard.bookingsproject.mappers.rentaltype.RentalTypeStructMapper;
import br.richard.bookingsproject.repositories.rentaltype.RentalTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Slf4j
@RequiredArgsConstructor
public class CreateRentalTypeUseCase {
    private final RentalTypeJpaRepository rentalTypeJpaRepository;
    private final RentalTypeStructMapper rentalTypeStructMapper;

    @Transactional
    public void execute(CreateRentalTypeInputDTO input){
        var rentalType = this.rentalTypeStructMapper.toEntity(input);

        log.warn("Creating rental type with name: {}", input.getName());
        rentalTypeJpaRepository.save(rentalType);
    }
}
