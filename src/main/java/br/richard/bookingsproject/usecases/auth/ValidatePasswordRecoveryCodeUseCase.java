package br.richard.bookingsproject.usecases.auth;

import br.richard.bookingsproject.dtos.auth.input.ValidatePasswordRecoveryCodeInputDTO;
import br.richard.bookingsproject.entities.User;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.exceptions.BusinessRuleException;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.repositories.user.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidatePasswordRecoveryCodeUseCase {
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public void execute(ValidatePasswordRecoveryCodeInputDTO input){
        var user = this.userJpaRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        if (!input.getCode().equals(user.getPasswordRecoveryCode()))
            throw new BusinessRuleException(ExceptionCode.INVALID_PASSWORD_RECOVERY_CODE);
    }
}
