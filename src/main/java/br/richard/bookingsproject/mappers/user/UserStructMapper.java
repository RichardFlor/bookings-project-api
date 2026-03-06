package br.richard.bookingsproject.mappers.user;


import br.richard.bookingsproject.dtos.user.input.CreateUserInputDTO;
import br.richard.bookingsproject.dtos.user.output.UserDetailedOutputDTO;
import br.richard.bookingsproject.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStructMapper {
    UserDetailedOutputDTO toUserDetailedOutputDTO(User entity);
    User toEntity(CreateUserInputDTO dto);
}
