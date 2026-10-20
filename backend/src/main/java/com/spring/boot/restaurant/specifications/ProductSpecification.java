package com.spring.boot.restaurant.specifications;

import com.spring.boot.restaurant.models.Product;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ProductSpecification {

    public static Specification<Product> search(
            LocalDateTime createdFrom,
            LocalDateTime createdTo,
            LocalDateTime updatedFrom,
            LocalDateTime updatedTo,
            String createdBy,
            String updatedBy
    ) {

        return (root, query, criteriaBuilder) -> {

            Specification<Product> specification =
                    Specification.where(null);

            if (createdFrom != null) {
                specification = specification.and(
                        (r, q, cb) ->
                                cb.greaterThanOrEqualTo(
                                        r.get("createdDate"),
                                        createdFrom
                                )
                );
            }

            if (createdTo != null) {
                specification = specification.and(
                        (r, q, cb) ->
                                cb.lessThanOrEqualTo(
                                        r.get("createdDate"),
                                        createdTo
                                )
                );
            }

            if (updatedFrom != null) {
                specification = specification.and(
                        (r, q, cb) ->
                                cb.greaterThanOrEqualTo(
                                        r.get("updatedDate"),
                                        updatedFrom
                                )
                );
            }

            if (updatedTo != null) {
                specification = specification.and(
                        (r, q, cb) ->
                                cb.lessThanOrEqualTo(
                                        r.get("updatedDate"),
                                        updatedTo
                                )
                );
            }

            if (createdBy != null && !createdBy.isBlank()) {
                specification = specification.and(
                        (r, q, cb) ->
                                cb.equal(
                                        r.get("createdBy"),
                                        createdBy
                                )
                );
            }

            if (updatedBy != null && !updatedBy.isBlank()) {
                specification = specification.and(
                        (r, q, cb) ->
                                cb.equal(
                                        r.get("updatedBy"),
                                        updatedBy
                                )
                );
            }

            return specification.toPredicate(
                    root,
                    query,
                    criteriaBuilder
            );
        };
    }
}