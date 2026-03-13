package br.richard.bookingsproject.repositories.rentaltype;

import br.richard.bookingsproject.entities.RentalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface RentalTypeJpaRepository extends JpaRepository<RentalType, UUID> {

    @Query("""
    SELECT rt
    FROM RentalType rt
    WHERE NOT EXISTS (
        SELECT r
        FROM Reservation r
        WHERE r.rentalType.id = rt.id
        AND r.reservationDate = :date
    )
""")
    Page<RentalType> findAvailableByDate(LocalDate date, Pageable pageable);
}