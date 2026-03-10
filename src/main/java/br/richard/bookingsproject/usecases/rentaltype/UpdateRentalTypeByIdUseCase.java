package br.richard.bookingsproject.usecases.rentaltype;

import br.richard.bookingsproject.dtos.rentaltype.input.UpdateRentalTypeByIdInputDTO;
import br.richard.bookingsproject.entities.RentalType;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.repositories.rentaltype.RentalTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateRentalTypeByIdUseCase {
    private final RentalTypeJpaRepository rentalTypeJpaRepository;

    @Transactional
    public void execute(UpdateRentalTypeByIdInputDTO input, UUID id){
        var rentalType = rentalTypeJpaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(RentalType.class));

        updateRentalTypeFields(rentalType, input);

        log.info("Editing rental type with id: {}", rentalType.getId());
        rentalTypeJpaRepository.save(rentalType);
    }

    private void updateRentalTypeFields(RentalType rentalType, UpdateRentalTypeByIdInputDTO input){
        rentalType.setName(input.getName());
        rentalType.setDescription(input.getDescription());
    }
}
