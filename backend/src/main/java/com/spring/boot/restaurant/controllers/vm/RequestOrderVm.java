package com.spring.boot.restaurant.controllers.vm;

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
public class RequestOrderVm {

    @NotEmpty(message = "error.products_ids.not_empty")
    private List<Long> productIds;

    @NotNull(message = "error.total_price.not_empty")
    private Double totalPrice;

    @NotNull(message = "error.total_number.not_empty")
    private Double totalNumber;
}