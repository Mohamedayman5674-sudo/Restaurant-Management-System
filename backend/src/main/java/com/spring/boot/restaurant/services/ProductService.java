package com.spring.boot.restaurant.services;

import com.spring.boot.restaurant.controllers.vm.ProductResponseVm;
import com.spring.boot.restaurant.dto.ProductDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductService {

    ProductResponseVm getAllProducts(
            int page,
            int size
    );

    ProductResponseVm getAllProductsByCategoryId(
            Long id,
            int page,
            int size
    );

    ProductDto createProduct(
            ProductDto productDto
    );

    List<ProductDto> createListOfProduct(
            List<ProductDto> productDto
    );

    ProductDto updateProduct(
            ProductDto productDto
    );

    List<ProductDto> updateListOfProduct(
            List<ProductDto> productDto
    );

    void deleteProductById(
            Long id
    );

    void deleteListOfProduct(
            List<Long> productIds
    );

    ProductDto getProductById(
            Long id
    );

    ProductResponseVm getAllProductsByKey(
            String key,
            int page,
            int size
    );

    ProductResponseVm getAllProductsByCategoryIdAndKey(
            Long categoryId,
            String key,
            int page,
            int size
    );

    List<ProductDto> getProductsByIds(
            List<Long> ids
    );

    ProductResponseVm searchProducts(
            LocalDateTime createdFrom,
            LocalDateTime createdTo,
            LocalDateTime updatedFrom,
            LocalDateTime updatedTo,
            String createdBy,
            String updatedBy,
            int page,
            int size
    );
}