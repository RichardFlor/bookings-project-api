package br.richard.bookingsproject.rest.controllers;

import br.richard.bookingsproject.dtos.auth.input.LoginInputDTO;
import br.richard.bookingsproject.dtos.auth.input.RequirePasswordRecoveryInputDTO;
import br.richard.bookingsproject.dtos.auth.input.ValidatePasswordRecoveryCodeInputDTO;
import br.richard.bookingsproject.dtos.auth.output.LoginOutputDTO;
import br.richard.bookingsproject.rest.specs.AuthControllerSpecs;
import br.richard.bookingsproject.usecases.auth.LoginUseCase;
import br.richard.bookingsproject.usecases.auth.RequirePasswordRecoveryUseCase;
import br.richard.bookingsproject.usecases.auth.ValidatePasswordRecoveryCodeUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController implements AuthControllerSpecs {
    private final LoginUseCase loginUseCase;
    private final RequirePasswordRecoveryUseCase requirePasswordRecoveryUseCase;
    private final ValidatePasswordRecoveryCodeUseCase validatePasswordRecoveryCodeUseCase;

    @PostMapping("/login")
    public LoginOutputDTO login(@RequestBody @Valid LoginInputDTO request) {
        return loginUseCase.execute(request);
    }

    @PatchMapping("/require-password-recovery")
    public void requirePasswordRecovery(@RequestBody @Valid RequirePasswordRecoveryInputDTO request) {
        requirePasswordRecoveryUseCase.execute(request);
    }

    @PatchMapping("/validate-password-recovery-code")
    public void validatePasswordRecoveryCode(@RequestBody @Valid ValidatePasswordRecoveryCodeInputDTO request) {
        validatePasswordRecoveryCodeUseCase.execute(request);
    }
}