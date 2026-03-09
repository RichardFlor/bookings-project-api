package br.richard.bookingsproject.repositories.rentaltype;

import br.richard.bookingsproject.entities.RentalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RentalTypeJpaRepository extends JpaRepository<RentalType, UUID> {
}
