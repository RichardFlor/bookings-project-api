package br.richard.bookingsproject.usecases.rentaltype;

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
public class DeleteRentalTypeByIdUseCase {
    private final RentalTypeJpaRepository rentalTypeJpaRepository;

    @Transactional
    public void execute(UUID id){
        var rentalType = this.rentalTypeJpaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(RentalType.class));

        log.info("Deleting rental type: {}", rentalType.getName());
        rentalTypeJpaRepository.delete(rentalType);
    }
}
