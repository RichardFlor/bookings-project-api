package br.richard.bookingsproject.usecases.user;

import br.richard.bookingsproject.dtos.user.output.UserDetailedOutputDTO;
import br.richard.bookingsproject.entities.User;
import br.richard.bookingsproject.errors.exceptions.EntityNotFoundException;
import br.richard.bookingsproject.mappers.user.UserStructMapper;
import br.richard.bookingsproject.repositories.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindUserByIdUseCase {
    private final UserJpaRepository userJpaRepository;
    private final UserStructMapper userStructMapper;

    public UserDetailedOutputDTO execute(UUID id) {
        var user = this.userJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        return this.userStructMapper.toUserDetailedOutputDTO(user);
    }
}
