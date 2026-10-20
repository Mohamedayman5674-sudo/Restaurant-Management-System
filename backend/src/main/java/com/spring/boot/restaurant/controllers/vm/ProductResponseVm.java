package com.spring.boot.restaurant.controllers.vm;

import com.spring.boot.restaurant.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseVm {
    private List<ProductDto> products;
    private Long totalProducts;
}
