package br.richard.bookingsproject.mappers.user;

import br.richard.bookingsproject.dtos.user.input.CreateUserInputDTO;
import br.richard.bookingsproject.dtos.user.output.UserDetailedOutputDTO;
import br.richard.bookingsproject.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserStructMapper {

    UserDetailedOutputDTO toUserDetailedOutputDTO(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "passwordRecoveryCode", ignore = true)
    @Mapping(target = "emailValidatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toEntity(CreateUserInputDTO dto);
}