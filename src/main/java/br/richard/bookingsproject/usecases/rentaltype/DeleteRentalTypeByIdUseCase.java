package br.richard.bookingsproject.usecases.rentaltype;

import br.richard.bookingsproject.entities.RentalType;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.exceptions.BusinessRuleException;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.repositories.rentaltype.RentalTypeJpaRepository;
import br.richard.bookingsproject.repositories.reservation.ReservationJpaRepository;
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
    private final ReservationJpaRepository reservationJpaRepository;

    @Transactional
    public void execute(UUID id){

        var rentalType = this.rentalTypeJpaRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(RentalType.class));

        if (reservationJpaRepository.existsByRentalTypeId(id)) {
            throw new BusinessRuleException(ExceptionCode.RENTAL_TYPE_HAS_RESERVATIONS);
        }

        log.info("Deleting rental type: {}", rentalType.getName());

        rentalTypeJpaRepository.delete(rentalType);
    }
}