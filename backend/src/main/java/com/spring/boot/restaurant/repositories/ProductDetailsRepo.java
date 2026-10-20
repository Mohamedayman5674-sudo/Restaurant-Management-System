package com.spring.boot.restaurant.repositories;

import com.spring.boot.restaurant.models.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductDetailsRepo
        extends JpaRepository<ProductDetails, Long> {

    boolean existsByProductId(Long productId);

    boolean existsByProductIdAndIdNot(
            Long productId,
            Long id
    );

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM ProductDetails p
            WHERE p.id = :id
            """)
    void deleteProductDetailsById(Long id);
}