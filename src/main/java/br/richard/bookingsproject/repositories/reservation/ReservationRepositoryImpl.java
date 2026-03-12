package br.richard.bookingsproject.repositories.reservation;

import br.richard.bookingsproject.dtos.reservation.input.FindReservationsByCurrentUserFiltersInputDTO;
import br.richard.bookingsproject.entities.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl {

    private final EntityManager entityManager;

    public Page<Reservation> findByUserId(UUID userId, FindReservationsByCurrentUserFiltersInputDTO filters) {

        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(Reservation.class);
        final var reservation = query.from(Reservation.class);

        final var pageLimit = filters.getLimit();
        final var pageNumber = filters.getPage();

        query.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(reservation.get("user").get("id"), userId),
                        getFilterConditions(criteriaBuilder, reservation, filters)
                )
        ).orderBy(criteriaBuilder.desc(reservation.get("createdAt")));

        final var typedQuery = entityManager.createQuery(query);
        typedQuery.setMaxResults(pageLimit);
        typedQuery.setFirstResult(pageNumber * pageLimit);

        final var totalElements = getTotalReservationsByFilters(criteriaBuilder, userId, filters);

        final var pageable = PageRequest.of(pageNumber, pageLimit);

        return new PageImpl<>(typedQuery.getResultList(), pageable, totalElements);
    }

    private Predicate getFilterConditions(
            CriteriaBuilder criteriaBuilder,
            Root<Reservation> reservation,
            FindReservationsByCurrentUserFiltersInputDTO filters
    ) {

        final var predicates = new ArrayList<Predicate>();

        Optional.ofNullable(filters.getReservationDate()).ifPresent(date ->
                predicates.add(criteriaBuilder.equal(reservation.get("reservationDate"), date))
        );

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Long getTotalReservationsByFilters(
            CriteriaBuilder criteriaBuilder,
            UUID userId,
            FindReservationsByCurrentUserFiltersInputDTO filters
    ) {

        var query = criteriaBuilder.createQuery(Long.class);
        var reservation = query.from(Reservation.class);

        var countQuery = query.select(criteriaBuilder.count(reservation));

        countQuery.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(reservation.get("user").get("id"), userId),
                        getFilterConditions(criteriaBuilder, reservation, filters)
                )
        );

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}