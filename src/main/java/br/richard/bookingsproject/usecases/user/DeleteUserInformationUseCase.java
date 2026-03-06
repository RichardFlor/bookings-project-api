package br.richard.bookingsproject.usecases.user;

import br.richard.bookingsproject.repositories.user.UserJpaRepository;
import br.richard.bookingsproject.services.AuthenticationContextServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserInformationUseCase {
    private final UserJpaRepository repository;
    private final AuthenticationContextServiceImpl authService;

    public void execute() {
        var user = authService.getLoggedUser();
        repository.deleteById(user.getId());
    }
}
