package br.richard.bookingsproject.mappers.rentaltype;

import br.richard.bookingsproject.dtos.rentaltype.input.CreateRentalTypeInputDTO;
import br.richard.bookingsproject.entities.RentalType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RentalTypeStructMapper {
    RentalType toEntity(CreateRentalTypeInputDTO dto);
}
