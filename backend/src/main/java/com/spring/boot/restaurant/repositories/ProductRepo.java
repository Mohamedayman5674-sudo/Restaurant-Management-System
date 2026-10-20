package com.spring.boot.restaurant.repositories;

import com.spring.boot.restaurant.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo
        extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

    Page<Product> findAllByOrderByIdAsc(
            Pageable pageable
    );

    Page<Product> findAllByOrderByIdDesc(
            Pageable pageable
    );

    @Query("""
            select p
            from Product p
            where p.category.id = :id
            order by p.id
            """)
    Page<Product> findAllProductsByCategoryIdByOrderByIdAsc(
            @Param("id") Long id,
            Pageable pageable
    );

    @Query("""
            SELECT p
            FROM Product p
            WHERE lower(p.name) LIKE lower(concat('%', :key, '%'))
               OR lower(p.description) LIKE lower(concat('%', :key, '%'))
            ORDER BY p.id
            """)
    Page<Product> getAllProductsByKeyByOrderByIdAsc(
            @Param("key") String key,
            Pageable pageable
    );

    @Query("""
            SELECT p
            FROM Product p
            WHERE p.category.id = :id
              AND (
                    lower(p.name) LIKE lower(concat('%', :key, '%'))
                    OR
                    lower(p.description) LIKE lower(concat('%', :key, '%'))
                  )
            ORDER BY p.id
            """)
    Page<Product> getAllProductsByKeyByCategoryIdByOrderByIdAsc(
            @Param("id") Long categoryId,
            @Param("key") String key,
            Pageable pageable
    );
}