package com.spring.boot.restaurant.repositories.security;

import com.spring.boot.restaurant.models.security.Role;
import com.spring.boot.restaurant.models.security.RoleCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByCode(RoleCode code);
}
