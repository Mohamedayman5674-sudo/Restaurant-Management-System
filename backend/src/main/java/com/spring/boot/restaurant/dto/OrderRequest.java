package com.spring.boot.restaurant.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderRequest {

    @NotEmpty(message = "products.not_empty")
    private List<Long> productIds;

    @NotNull(message = "total_price.not_null")
    private Double totalPrice;

    @NotNull(message = "total_number.not_null")
    private Double totalNumber;
}