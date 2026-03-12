package br.richard.bookingsproject.repositories.rentaltype;

import br.richard.bookingsproject.dtos.rentaltype.input.FindRentalTypesByFiltersInputDTO;
import br.richard.bookingsproject.entities.RentalType;
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

@Repository
@RequiredArgsConstructor
public class RentalTypeRepositoryImpl {

    private final EntityManager entityManager;

    public Page<RentalType> findAll(FindRentalTypesByFiltersInputDTO filters) {

        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(RentalType.class);
        final var rentalType = query.from(RentalType.class);

        final var pageLimit = filters.getLimit();
        final var pageNumber = filters.getPage();

        query.where(getFilterConditions(criteriaBuilder, rentalType, filters))
                .orderBy(criteriaBuilder.desc(rentalType.get("createdAt")));

        final var typedQuery = entityManager.createQuery(query);
        typedQuery.setMaxResults(pageLimit);
        typedQuery.setFirstResult(pageNumber * pageLimit);

        final var totalElements = getTotalRentalTypesByFilters(criteriaBuilder, filters);

        final var pageable = PageRequest.of(pageNumber, pageLimit);

        return new PageImpl<>(typedQuery.getResultList(), pageable, totalElements);
    }

    private Predicate getFilterConditions(
            CriteriaBuilder criteriaBuilder,
            Root<RentalType> rentalType,
            FindRentalTypesByFiltersInputDTO filters
    ) {

        final var predicates = new ArrayList<Predicate>();

        Optional.ofNullable(filters.getName()).ifPresent(name -> {
            var rentalName = criteriaBuilder.lower(rentalType.get("name"));
            predicates.add(criteriaBuilder.like(rentalName, "%" + name.toLowerCase() + "%"));
        });

        Optional.ofNullable(filters.getDescription()).ifPresent(description -> {
            var rentalDescription = criteriaBuilder.lower(rentalType.get("description"));
            predicates.add(criteriaBuilder.like(rentalDescription, "%" + description.toLowerCase() + "%"));
        });

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Long getTotalRentalTypesByFilters(
            CriteriaBuilder criteriaBuilder,
            FindRentalTypesByFiltersInputDTO filters
    ) {

        var query = criteriaBuilder.createQuery(Long.class);
        var rentalTypes = query.from(RentalType.class);

        var countQuery = query.select(criteriaBuilder.count(rentalTypes));

        countQuery.where(getFilterConditions(criteriaBuilder, rentalTypes, filters));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

}