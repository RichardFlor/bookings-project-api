package br.richard.bookingsproject.mappers.rentaltype;

import br.richard.bookingsproject.dtos.rentaltype.input.CreateRentalTypeInputDTO;
import br.richard.bookingsproject.dtos.rentaltype.output.RentalTypeOutputDTO;
import br.richard.bookingsproject.entities.RentalType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalTypeStructMapper {
    RentalType toEntity(CreateRentalTypeInputDTO dto);

    List<RentalTypeOutputDTO> toRentalTypeOutputDTO(List<RentalType> entities);
}
