package com.spring.boot.restaurant.controllers.vm;

import com.spring.boot.restaurant.dto.ProductDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDetailsResponseVm {

    private List<ProductDetailsDto> productDetails;

    private long totalProductDetails;
}