package br.richard.bookingsproject.rest.controllers;

import br.richard.bookingsproject.dtos.user.input.ChangePasswordInputDTO;
import br.richard.bookingsproject.dtos.user.input.CreateUserInputDTO;
import br.richard.bookingsproject.dtos.user.output.UserDetailedOutputDTO;
import br.richard.bookingsproject.rest.specs.UserControllerSpecs;
import br.richard.bookingsproject.usecases.user.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserControllerSpecs {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final ValidateEmailUseCase validateEmailUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final DeleteUserInformationUseCase deleteUserInformationUseCase;
    private final DeleteUserByIdUseCase deleteUserByIdUseCase;

    @PostMapping
    public void create(@RequestBody @Valid CreateUserInputDTO request) {
        this.createUserUseCase.execute(request);
    }

    @PatchMapping("/change-password")
    public void passwordRecovery(@RequestBody @Valid ChangePasswordInputDTO request) {
        this.changePasswordUseCase.execute(request);
    }

    @GetMapping("/{id}")
    public UserDetailedOutputDTO findById(@PathVariable UUID id) {
        return this.findUserByIdUseCase.execute(id);
    }

    @GetMapping("/{id}/validate-email")
    public String validateEmail(@PathVariable UUID id) {
        return this.validateEmailUseCase.execute(id);
    }

    @DeleteMapping
    public void delete() {
        this.deleteUserInformationUseCase.execute();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        this.deleteUserByIdUseCase.execute(id);
    }
}