package com.spring.boot.restaurant.services;

import com.spring.boot.restaurant.controllers.vm.ProductDetailsResponseVm;
import com.spring.boot.restaurant.dto.ProductDetailsDto;

public interface ProductDetailsService {

    ProductDetailsResponseVm getAllProductDetails(
            int page,
            int size
    );

    ProductDetailsDto addProductDetails(
            ProductDetailsDto productDetailsDto
    );

    ProductDetailsDto updateProductDetails(
            ProductDetailsDto productDetailsDto
    );

    void deleteProductDetails(Long id);
}