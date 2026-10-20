package com.spring.boot.restaurant.services.security;

import com.spring.boot.restaurant.dto.security.RoleDto;

public interface RoleService {
    RoleDto findByRole(String role);
}
