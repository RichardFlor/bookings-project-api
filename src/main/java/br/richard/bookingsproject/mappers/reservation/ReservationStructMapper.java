package br.richard.bookingsproject.mappers.reservation;

import br.richard.bookingsproject.dtos.reservation.input.CreateReservationInputDTO;
import br.richard.bookingsproject.entities.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationStructMapper {
    Reservation toEntity(CreateReservationInputDTO dto);
}
