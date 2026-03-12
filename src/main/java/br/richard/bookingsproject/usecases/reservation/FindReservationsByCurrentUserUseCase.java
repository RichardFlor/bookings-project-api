package br.richard.bookingsproject.usecases.reservation;

import br.richard.bookingsproject.dtos.reservation.output.FindReservationsByCurrentUserOutputDTO;
import br.richard.bookingsproject.repositories.reservation.ReservationJpaRepository;
import br.richard.bookingsproject.services.AuthenticationContextServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindReservationsByCurrentUserUseCase {
    private final ReservationJpaRepository reservationJpaRepository;
    private final AuthenticationContextServiceImpl authService;

    public Set<FindReservationsByCurrentUserOutputDTO> execute() {

        var user = authService.getLoggedUser();

        var reservations = reservationJpaRepository.findByUserId(user.getId());

        return reservations.stream()
                .map(reservation -> FindReservationsByCurrentUserOutputDTO.builder()
                        .id(reservation.getId())
                        .rentalTypeName(reservation.getRentalType().getName())
                        .reservationDate(reservation.getReservationDate())
                        .createdAt(reservation.getCreatedAt())
                        .build())
                .collect(Collectors.toSet());
    }
}

