package br.richard.bookingsproject.repositories.user;

import br.richard.bookingsproject.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl {
    private final EntityManager entityManager;
    private final UserJpaRepository repository;

    private Optional<User> findBy(String field, Object value) {
        if (Objects.isNull(field))
            return Optional.empty();

        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(User.class);
        final var user = query.from(User.class);

        var activeUserConditions = getUserStatusPredicate(criteriaBuilder, user, true);

        query.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(user.get(field), value),
                        activeUserConditions
                )
        );

        final var results = entityManager.createQuery(query).getResultList();
        return results.stream().findFirst();
    }

    private Predicate getUserStatusPredicate(CriteriaBuilder criteriaBuilder, Root<User> user, Boolean onlyActiveUsers) {
        Predicate condition;

        if (Objects.isNull(onlyActiveUsers))
            return criteriaBuilder.conjunction();

        condition = criteriaBuilder.or(
                criteriaBuilder.isNotNull(user.get("deletedAt"))
        );

        if (onlyActiveUsers)
            condition = criteriaBuilder.and(
                    criteriaBuilder.isNull(user.get("deletedAt"))
            );

        return condition;
    }

    public Optional<User> findById(UUID id) {
        return this.findBy("id", id);
    }
}