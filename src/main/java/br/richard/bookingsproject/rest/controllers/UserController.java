package br.richard.bookingsproject.rest.controllers;

import br.richard.bookingsproject.dtos.user.input.ChangePasswordInputDTO;
import br.richard.bookingsproject.dtos.user.input.CreateUserInputDTO;
import br.richard.bookingsproject.dtos.user.output.UserDetailedOutputDTO;
import br.richard.bookingsproject.rest.specs.UserControllerSpecs;
import br.richard.bookingsproject.usecases.user.ChangePasswordUseCase;
import br.richard.bookingsproject.usecases.user.CreateUserUseCase;
import br.richard.bookingsproject.usecases.user.DeleteUserInformationUseCase;
import br.richard.bookingsproject.usecases.user.FindUserByIdUseCase;
import br.richard.bookingsproject.usecases.user.ValidateEmailUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserControllerSpecs {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final ValidateEmailUseCase validateEmailUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final DeleteUserInformationUseCase deleteUserInformationUseCase;

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
}