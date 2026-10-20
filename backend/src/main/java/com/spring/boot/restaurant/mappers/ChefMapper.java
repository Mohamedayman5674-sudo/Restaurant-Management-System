package com.spring.boot.restaurant.mappers;

import com.spring.boot.restaurant.dto.ChefDto;
import com.spring.boot.restaurant.models.Chef;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChefMapper {

    ChefMapper CHEF_MAPPER =
            Mappers.getMapper(ChefMapper.class);

    ChefDto toChefDto(Chef chef);

    Chef toChef(ChefDto chefDto);
}