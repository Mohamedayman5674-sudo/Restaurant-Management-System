package com.spring.boot.restaurant.repositories.security;

import com.spring.boot.restaurant.models.security.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

    List<Account> findAllByOrderByIdAsc();

    Page<Account> findAllByOrderByIdAsc(Pageable pageable);
}