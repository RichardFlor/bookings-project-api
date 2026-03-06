package br.richard.bookingsproject.entities;

import br.richard.bookingsproject.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private String password;

    private String passwordRecoveryCode;

    private LocalDateTime emailValidatedAt;
}
