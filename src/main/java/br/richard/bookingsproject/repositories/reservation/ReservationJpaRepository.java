package br.richard.bookingsproject.repositories.reservation;

import br.richard.bookingsproject.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ReservationJpaRepository extends JpaRepository<Reservation, UUID> {

    boolean existsByRentalTypeIdAndReservationDate(UUID rentalTypeId, LocalDate reservationDate);
}
