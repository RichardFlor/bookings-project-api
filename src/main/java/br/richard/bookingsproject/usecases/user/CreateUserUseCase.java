package br.richard.bookingsproject.usecases.user;

import br.richard.bookingsproject.dtos.email.input.SendEmailInputDTO;
import br.richard.bookingsproject.dtos.user.input.CreateUserInputDTO;
import br.richard.bookingsproject.entities.User;
import br.richard.bookingsproject.enums.EmailTemplate;
import br.richard.bookingsproject.enums.UserRole;
import br.richard.bookingsproject.errors.exceptions.DuplicatedResourceException;
import br.richard.bookingsproject.mappers.user.UserStructMapper;
import br.richard.bookingsproject.repositories.user.UserJpaRepository;
import br.richard.bookingsproject.services.SmtpEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserJpaRepository userJpaRepository;
    private final SmtpEmailService smtpEmailService;
    private final UserStructMapper userStructMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(CreateUserInputDTO input) {

        if (userJpaRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new DuplicatedResourceException(User.class, Map.of("email", input.getEmail()));
        }

        var user = userStructMapper.toEntity(input)
                .withRole(UserRole.CUSTOMER);

        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        log.info("Creating user with email: {}", input.getEmail());

        var createdUser = userJpaRepository.save(user);

        sendEmailToValidation(createdUser);
    }

    private void sendEmailToValidation(User user) {

        Map<String, Object> data = new HashMap<>();
        data.put("link", generateUrlToValidateEmail(user.getId()));

        var template = smtpEmailService.processTemplate(data, EmailTemplate.EMAIL_VALIDATION);

        var emailMessage = new SendEmailInputDTO(
                List.of(user.getEmail()),
                "Validação de E-mail",
                template
        );

        smtpEmailService.sendEmail(emailMessage);
    }

    private String generateUrlToValidateEmail(UUID userId) {

        var baseUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        return MessageFormat.format(
                "{0}/users/{1}/validate-email",
                baseUrl,
                userId
        );
    }
}