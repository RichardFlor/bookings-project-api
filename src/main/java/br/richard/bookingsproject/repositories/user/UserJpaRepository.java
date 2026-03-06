package br.richard.bookingsproject.repositories.user;

import br.richard.bookingsproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID> {
    Optional<User>findByEmail(String email);
}
