package com.spring.boot.restaurant.repositories;

import com.spring.boot.restaurant.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo
        extends JpaRepository<Category, Long>,
        JpaSpecificationExecutor<Category> {

    Page<Category> findAllByOrderByNameAsc(
            Pageable pageable
    );

    Page<Category> findAllByOrderByIdDesc(
            Pageable pageable
    );
}