package com.spring.boot.restaurant.controllers.vm;

import com.spring.boot.restaurant.dto.CategoryDto;
import com.spring.boot.restaurant.dto.OrderDto;
import com.spring.boot.restaurant.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardResponseVm {

    private long totalUsers;

    private long totalProducts;

    private long totalCategories;

    private long totalChefs;

    private long totalOrders;

    private List<OrderDto> recentOrders;

    private List<ProductDto> recentProducts;

    private List<CategoryDto> recentCategories;
}