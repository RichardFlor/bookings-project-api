package br.richard.bookingsproject.services;

import br.richard.bookingsproject.entities.User;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.errors.exceptions.ForbiddenException;
import br.richard.bookingsproject.security.dto.UserDetailsDTO;
import br.richard.bookingsproject.usecases.user.FindUserByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationContextServiceImpl {
    private final FindUserByIdUseCase findUserByIdUseCase;

    public UserDetailsDTO getData() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Optional)
            return this.handleOptionalData((Optional<UserDetailsDTO>) principal);

        if (principal instanceof UserDetailsDTO)
            return (UserDetailsDTO) principal;

        throw new ForbiddenException();
    }

    private UserDetailsDTO handleOptionalData(Optional<UserDetailsDTO> principal) {
        principal.orElseThrow(() -> new EntityNotFoundException(UserDetailsDTO.class));
        return principal.get();
    }

    public User getLoggedUser() {
        return this.getData().getUser();
    }
}
