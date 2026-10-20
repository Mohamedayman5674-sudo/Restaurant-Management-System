package com.spring.boot.restaurant.controllers.vm;

import com.spring.boot.restaurant.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderResponseVm {

    private List<OrderDto> orders;

    private long totalElements;
}