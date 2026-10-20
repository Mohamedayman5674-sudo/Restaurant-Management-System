package com.spring.boot.restaurant.mappers;

import com.spring.boot.restaurant.dto.ProductDto;
import com.spring.boot.restaurant.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper PRODUCT_MAPPER =
            Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.id", target = "category.id")
    @Mapping(source = "category.name", target = "category.name")
    @Mapping(source = "category.logo", target = "category.logo")
    @Mapping(source = "category.flag", target = "category.flag")
    ProductDto toProductDto(Product product);

    @Mapping(source = "category.id", target = "category.id")
    Product toProduct(ProductDto productDto);

    List<ProductDto> toDtoList(List<Product> products);

    List<Product> toEntityList(List<ProductDto> productDtos);
}