package com.spring.boot.restaurant.services.impl;

import com.spring.boot.restaurant.controllers.vm.OrderResponseVm;
import com.spring.boot.restaurant.controllers.vm.RequestOrderVm;
import com.spring.boot.restaurant.controllers.vm.ResponseOrderVm;
import com.spring.boot.restaurant.dto.OrderDto;
import com.spring.boot.restaurant.dto.ProductDto;
import com.spring.boot.restaurant.dto.security.AccountDto;
import com.spring.boot.restaurant.mappers.ProductMapper;
import com.spring.boot.restaurant.mappers.security.AccountMapper;
import com.spring.boot.restaurant.models.Order;
import com.spring.boot.restaurant.repositories.OrderRepo;
import com.spring.boot.restaurant.services.OrderService;
import com.spring.boot.restaurant.services.ProductService;
import com.spring.boot.restaurant.specifications.OrderSpecification;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductService productService;

    @Override
    @CacheEvict(
            value = {"orders", "myOrders", "orderSearch"},
            allEntries = true
    )
    public ResponseOrderVm requestOrder(
            RequestOrderVm requestOrderVm) {

        try {

            // user
            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            AccountDto accountDto =
                    (AccountDto) authentication.getPrincipal();

            // products
            List<Long> productIds =
                    requestOrderVm.getProductIds();

            List<ProductDto> productDtos =
                    productService.getProductsByIds(productIds);

            // order
            Order order = new Order();

            order.setProducts(
                    ProductMapper.PRODUCT_MAPPER
                            .toEntityList(productDtos)
            );

            order.setAccount(
                    AccountMapper.ACCOUNT_MAPPER
                            .toAccount(accountDto)
            );

            order.setTotalPrice(
                    requestOrderVm.getTotalPrice()
            );

            order.setTotalNumber(
                    requestOrderVm.getTotalNumber()
            );

            // save first to generate id
            order = orderRepo.save(order);

            // generate code
            String code = getCode(order);

            order.setCode(code);

            orderRepo.save(order);

            return new ResponseOrderVm(
                    code,
                    order.getTotalPrice(),
                    order.getTotalNumber()
            );

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Cacheable(
            value = "myOrders",
            key = "#page + '-' + #size + '-' + T(com.spring.boot.restaurant.services.impl.OrderServiceImpl).getLoggedInUsername()"
    )
    public OrderResponseVm getOrdersForLoggedInUser(
            int page,
            int size) {

        try {

            if (page < 1) {
                throw new SystemException(
                        "error.min.one.page"
                );
            }

            if (size < 1) {
                throw new SystemException(
                        "error.min.one.page"
                );
            }

            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            AccountDto accountDto =
                    (AccountDto) authentication.getPrincipal();

            String username =
                    accountDto.getUsername();

            Pageable pageable =
                    PageRequest.of(
                            page - 1,
                            size
                    );

            Page<Order> ordersPage =
                    orderRepo
                            .findAllByAccountUsernameOrderByIdAsc(
                                    username,
                                    pageable
                            );

            if (ordersPage.isEmpty()) {
                throw new SystemException(
                        "orders.not.found"
                );
            }

            List<OrderDto> orders =
                    ordersPage.getContent()
                            .stream()
                            .map(this::toOrderDto)
                            .toList();

            return new OrderResponseVm(
                    orders,
                    ordersPage.getTotalElements()
            );

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Cacheable(
            value = "orders",
            key = "'all-' + #page + '-' + #size"
    )
    public OrderResponseVm getAllOrders(
            int page,
            int size) {

        try {

            if (page < 1) {
                throw new SystemException(
                        "error.min.one.page"
                );
            }

            if (size < 1) {
                throw new SystemException(
                        "error.min.one.page"
                );
            }

            Pageable pageable =
                    PageRequest.of(
                            page - 1,
                            size
                    );

            Page<Order> ordersPage =
                    orderRepo.findAllByOrderByIdAsc(
                            pageable
                    );

            if (ordersPage.isEmpty()) {
                throw new SystemException(
                        "orders.not.found"
                );
            }

            List<OrderDto> orders =
                    ordersPage.getContent()
                            .stream()
                            .map(this::toOrderDto)
                            .toList();

            return new OrderResponseVm(
                    orders,
                    ordersPage.getTotalElements()
            );

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private OrderDto toOrderDto(Order order) {

        OrderDto orderDto = new OrderDto();

        orderDto.setId(
                order.getId()
        );

        orderDto.setCode(
                order.getCode()
        );

        orderDto.setTotalPrice(
                order.getTotalPrice()
        );

        orderDto.setTotalNumber(
                order.getTotalNumber()
        );

        orderDto.setProducts(
                ProductMapper.PRODUCT_MAPPER
                        .toDtoList(
                                order.getProducts()
                        )
        );

        return orderDto;
    }

    private String getCode(Order order) {
        return "RES-" + order.getId();
    }

    public static String getLoggedInUsername() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || authentication.getPrincipal() == null) {
            return "anonymous";
        }

        Object principal =
                authentication.getPrincipal();

        if (principal instanceof AccountDto) {

            AccountDto accountDto =
                    (AccountDto) principal;

            return accountDto.getUsername();
        }

        return "anonymous";
    }

    @Override
    @Cacheable(
            value = "orderSearch",
            key = "#createdFrom + '-' + #createdTo + '-' + #updatedFrom + '-' + #updatedTo + '-' + #createdBy + '-' + #updatedBy + '-' + #page + '-' + #size"
    )
    public OrderResponseVm searchOrders(
            LocalDateTime createdFrom,
            LocalDateTime createdTo,
            LocalDateTime updatedFrom,
            LocalDateTime updatedTo,
            String createdBy,
            String updatedBy,
            int page,
            int size
    ) {

        try {

            if (page < 1 || size < 1) {
                throw new SystemException(
                        "error.min.one.page"
                );
            }

            Pageable pageable =
                    PageRequest.of(page - 1, size);

            Specification<Order> specification =
                    OrderSpecification.search(
                            createdFrom,
                            createdTo,
                            updatedFrom,
                            updatedTo,
                            createdBy,
                            updatedBy
                    );

            Page<Order> ordersPage =
                    orderRepo.findAll(
                            specification,
                            pageable
                    );

            if (ordersPage.isEmpty()) {
                throw new SystemException(
                        "orders.not.found"
                );
            }

            List<OrderDto> orders =
                    ordersPage.getContent()
                            .stream()
                            .map(order -> {

                                OrderDto orderDto =
                                        new OrderDto();

                                orderDto.setId(
                                        order.getId()
                                );

                                orderDto.setCode(
                                        order.getCode()
                                );

                                orderDto.setTotalPrice(
                                        order.getTotalPrice()
                                );

                                orderDto.setTotalNumber(
                                        order.getTotalNumber()
                                );

                                orderDto.setProducts(
                                        order.getProducts()
                                                .stream()
                                                .map(
                                                        ProductMapper
                                                                .PRODUCT_MAPPER
                                                                ::toProductDto
                                                )
                                                .toList()
                                );

                                return orderDto;
                            })
                            .toList();

            return new OrderResponseVm(
                    orders,
                    ordersPage.getTotalElements()
            );

        } catch (SystemException e) {

            throw new RuntimeException(
                    e.getMessage()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }
}