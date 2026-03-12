package br.richard.bookingsproject.usecases.user;

import br.richard.bookingsproject.entities.User;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.exceptions.BusinessRuleException;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.repositories.reservation.ReservationJpaRepository;
import br.richard.bookingsproject.repositories.user.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteUserByIdUseCase {
    private final UserJpaRepository userJpaRepository;
    private final ReservationJpaRepository reservationJpaRepository;

    @Transactional
    public void execute(UUID id){

        var user = this.userJpaRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        if (reservationJpaRepository.existsByUserId(id)) {
            throw new BusinessRuleException(ExceptionCode.USER_HAS_RESERVATIONS);
        }

        log.info("Deleting user: {}", user.getEmail());

        userJpaRepository.delete(user);
    }
}