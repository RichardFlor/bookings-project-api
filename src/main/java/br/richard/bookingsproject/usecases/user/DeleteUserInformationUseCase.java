package br.richard.bookingsproject.usecases.user;

import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.exceptions.BusinessRuleException;
import br.richard.bookingsproject.repositories.reservation.ReservationJpaRepository;
import br.richard.bookingsproject.repositories.user.UserJpaRepository;
import br.richard.bookingsproject.services.AuthenticationContextServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserInformationUseCase {
    private final UserJpaRepository repository;
    private final ReservationJpaRepository reservationJpaRepository;
    private final AuthenticationContextServiceImpl authService;

    public void execute() {

        var user = authService.getLoggedUser();

        if (reservationJpaRepository.existsByUserId(user.getId())) {
            throw new BusinessRuleException(ExceptionCode.USER_HAS_RESERVATIONS);
        }

        repository.deleteById(user.getId());
    }
}