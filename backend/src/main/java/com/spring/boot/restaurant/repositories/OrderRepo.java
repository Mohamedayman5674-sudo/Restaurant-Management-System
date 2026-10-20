package com.spring.boot.restaurant.repositories;

import com.spring.boot.restaurant.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo
        extends JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

    Page<Order> findAllByAccountUsernameOrderByIdAsc(
            String username,
            Pageable pageable
    );

    Page<Order> findAllByOrderByIdAsc(
            Pageable pageable
    );

    Page<Order> findAllByOrderByIdDesc(
            Pageable pageable
    );
}