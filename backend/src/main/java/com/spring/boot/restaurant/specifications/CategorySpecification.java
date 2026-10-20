package com.spring.boot.restaurant.specifications;

import com.spring.boot.restaurant.models.Category;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CategorySpecification {

    public static Specification<Category> search(
            LocalDateTime createdFrom,
            LocalDateTime createdTo,
            LocalDateTime updatedFrom,
            LocalDateTime updatedTo,
            String createdBy,
            String updatedBy) {

        return (root, query, criteriaBuilder) -> {

            Specification<Category> specification =
                    Specification.where(null);

            // Created Date From
            if (createdFrom != null) {

                specification = specification.and(
                        (r, q, cb) ->
                                cb.greaterThanOrEqualTo(
                                        r.get("createdDate"),
                                        createdFrom
                                )
                );
            }

            // Created Date To
            if (createdTo != null) {

                specification = specification.and(
                        (r, q, cb) ->
                                cb.lessThanOrEqualTo(
                                        r.get("createdDate"),
                                        createdTo
                                )
                );
            }

            // Updated Date From
            if (updatedFrom != null) {

                specification = specification.and(
                        (r, q, cb) ->
                                cb.greaterThanOrEqualTo(
                                        r.get("updatedDate"),
                                        updatedFrom
                                )
                );
            }

            // Updated Date To
            if (updatedTo != null) {

                specification = specification.and(
                        (r, q, cb) ->
                                cb.lessThanOrEqualTo(
                                        r.get("updatedDate"),
                                        updatedTo
                                )
                );
            }

            // Created By
            if (createdBy != null
                    && !createdBy.isBlank()) {

                specification = specification.and(
                        (r, q, cb) ->
                                cb.equal(
                                        r.get("createdBy"),
                                        createdBy
                                )
                );
            }

            // Updated By
            if (updatedBy != null
                    && !updatedBy.isBlank()) {

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