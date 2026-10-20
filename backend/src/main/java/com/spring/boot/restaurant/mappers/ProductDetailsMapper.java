package com.spring.boot.restaurant.mappers;

import com.spring.boot.restaurant.dto.ProductDetailsDto;
import com.spring.boot.restaurant.models.ProductDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductDetailsMapper {

    ProductDetailsMapper PRODUCT_DETAILS_MAPPER =
            Mappers.getMapper(ProductDetailsMapper.class);

    @Mapping(source = "product.id", target = "productId")
    ProductDetailsDto toProductDetailsDto(ProductDetails productDetails);
}