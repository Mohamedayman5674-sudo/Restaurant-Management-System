package com.spring.boot.restaurant.repositories;

import com.spring.boot.restaurant.models.Chef;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepo extends JpaRepository<Chef, Long> {

    Page<Chef> findAllByOrderByIdAsc(Pageable pageable);
}