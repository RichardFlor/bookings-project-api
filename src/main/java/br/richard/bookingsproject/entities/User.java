package br.richard.bookingsproject.entities;

import br.richard.bookingsproject.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
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

    @NotBlank
    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private UserRole role;

    @NotBlank
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "password_recovery_code", length = 100)
    private String passwordRecoveryCode;

    @Column(name = "email_validated_at")
    private LocalDateTime emailValidatedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}