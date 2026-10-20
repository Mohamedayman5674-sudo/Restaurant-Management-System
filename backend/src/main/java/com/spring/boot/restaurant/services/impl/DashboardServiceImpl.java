package com.spring.boot.restaurant.services.impl;

import com.spring.boot.restaurant.controllers.vm.DashboardResponseVm;
import com.spring.boot.restaurant.dto.CategoryDto;
import com.spring.boot.restaurant.dto.OrderDto;
import com.spring.boot.restaurant.dto.ProductDto;
import com.spring.boot.restaurant.mappers.CategoryMapper;
import com.spring.boot.restaurant.mappers.ProductMapper;
import com.spring.boot.restaurant.repositories.CategoryRepo;
import com.spring.boot.restaurant.repositories.ChefRepo;
import com.spring.boot.restaurant.repositories.OrderRepo;
import com.spring.boot.restaurant.repositories.ProductRepo;
import com.spring.boot.restaurant.repositories.security.AccountRepo;
import com.spring.boot.restaurant.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ChefRepo chefRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public DashboardResponseVm getDashboard() {

        /*
         * Get last 5 records
         */
        Pageable recentPageable =
                PageRequest.of(
                        0,
                        5,
                        Sort.by(Sort.Direction.DESC, "id")
                );

        /*
         * Recent Products
         */
        Page<com.spring.boot.restaurant.models.Product> productsPage =
                productRepo.findAll(recentPageable);

        List<ProductDto> recentProducts =
                productsPage.getContent()
                        .stream()
                        .map(ProductMapper.PRODUCT_MAPPER::toProductDto)
                        .toList();

        /*
         * Recent Categories
         */
        Page<com.spring.boot.restaurant.models.Category> categoriesPage =
                categoryRepo.findAll(recentPageable);

        List<CategoryDto> recentCategories =
                categoriesPage.getContent()
                        .stream()
                        .map(CategoryMapper.CATEGORY_MAPPER::toCategoryDto)
                        .toList();

        /*
         * Recent Orders
         */
        Page<com.spring.boot.restaurant.models.Order> ordersPage =
                orderRepo.findAll(recentPageable);

        List<OrderDto> recentOrders =
                ordersPage.getContent()
                        .stream()
                        .map(order -> {

                            OrderDto orderDto = new OrderDto();

                            orderDto.setId(order.getId());
                            orderDto.setCode(order.getCode());
                            orderDto.setTotalPrice(order.getTotalPrice());
                            orderDto.setTotalNumber(order.getTotalNumber());

                            orderDto.setProducts(
                                    order.getProducts()
                                            .stream()
                                            .map(ProductMapper.PRODUCT_MAPPER::toProductDto)
                                            .toList()
                            );

                            return orderDto;
                        })
                        .toList();

        /*
         * Return Dashboard
         */
        return new DashboardResponseVm(

                accountRepo.count(),

                productRepo.count(),

                categoryRepo.count(),

                chefRepo.count(),

                orderRepo.count(),

                recentOrders,

                recentProducts,

                recentCategories
        );
    }
}