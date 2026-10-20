package com.spring.boot.restaurant.mappers.security;

import com.spring.boot.restaurant.dto.security.RoleDto;
import com.spring.boot.restaurant.models.security.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper ROLE_MAPPER = Mappers.getMapper(RoleMapper.class);

    Role toRole(RoleDto roleDto);

    RoleDto toRoleDto(Role role);
}