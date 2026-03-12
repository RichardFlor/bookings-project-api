package br.richard.bookingsproject.usecases.reservation;

import br.richard.bookingsproject.dtos.commons.pagination.output.PaginationOutputDTO;
import br.richard.bookingsproject.dtos.reservation.input.FindReservationsByCurrentUserFiltersInputDTO;
import br.richard.bookingsproject.dtos.reservation.output.FindReservationsByCurrentUserOutputDTO;
import br.richard.bookingsproject.repositories.reservation.ReservationRepositoryImpl;
import br.richard.bookingsproject.services.AuthenticationContextServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindReservationsByCurrentUserUseCase {

    private final ReservationRepositoryImpl reservationRepository;
    private final AuthenticationContextServiceImpl authService;

    public PaginationOutputDTO<FindReservationsByCurrentUserOutputDTO> execute(
            FindReservationsByCurrentUserFiltersInputDTO filters
    ) {

        var user = authService.getLoggedUser();

        var page = reservationRepository.findByUserId(user.getId(), filters);

        var content = page.getContent().stream()
                .map(reservation -> FindReservationsByCurrentUserOutputDTO.builder()
                        .id(reservation.getId())
                        .rentalTypeName(reservation.getRentalType().getName())
                        .reservationDate(reservation.getReservationDate())
                        .createdAt(reservation.getCreatedAt())
                        .build())
                .toList();

        return new PaginationOutputDTO<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}