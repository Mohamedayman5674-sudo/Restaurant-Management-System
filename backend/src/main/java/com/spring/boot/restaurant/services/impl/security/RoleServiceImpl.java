package com.spring.boot.restaurant.services.impl.security;

import com.spring.boot.restaurant.dto.security.RoleDto;
import com.spring.boot.restaurant.mappers.security.RoleMapper;
import com.spring.boot.restaurant.models.security.Role;
import com.spring.boot.restaurant.models.security.RoleCode;
import com.spring.boot.restaurant.repositories.security.RoleRepo;
import com.spring.boot.restaurant.services.security.RoleService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    @Cacheable(
            value = "roles",
            key = "'role-' + #role.toUpperCase()"
    )
    public RoleDto findByRole(String role) {

        try {

            RoleCode roleCode =
                    RoleCode.valueOf(
                            role.toUpperCase()
                    );

            Optional<Role> roleResult =
                    roleRepo.findByCode(roleCode);

            if (roleResult.isEmpty()) {

                throw new SystemException(
                        "error.role.not.found"
                );
            }

            return RoleMapper.ROLE_MAPPER
                    .toRoleDto(
                            roleResult.get()
                    );

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }
}