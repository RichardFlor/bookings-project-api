package br.richard.bookingsproject.usecases.user;

import br.richard.bookingsproject.dtos.user.input.ChangePasswordInputDTO;
import br.richard.bookingsproject.entities.User;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.exceptions.BusinessRuleException;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.repositories.user.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangePasswordUseCase {
    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Transactional
    public void execute(ChangePasswordInputDTO input) {
        var user = userJpaRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        validate(user, input);

        user.setPasswordRecoveryCode(null);
        user.setPassword(bcryptPasswordEncoder.encode(input.getPassword()));

        userJpaRepository.save(user);
    }

    private void validate(User user, ChangePasswordInputDTO input) {
        if (!input.getCode().equals(user.getPasswordRecoveryCode()))
            throw new BusinessRuleException(ExceptionCode.INVALID_PASSWORD_RECOVERY_CODE);
    }
}
