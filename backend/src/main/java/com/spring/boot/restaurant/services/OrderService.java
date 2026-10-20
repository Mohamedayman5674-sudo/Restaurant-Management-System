package com.spring.boot.restaurant.services;

import com.spring.boot.restaurant.controllers.vm.OrderResponseVm;
import com.spring.boot.restaurant.controllers.vm.RequestOrderVm;
import com.spring.boot.restaurant.controllers.vm.ResponseOrderVm;

import java.time.LocalDateTime;

public interface OrderService {

    ResponseOrderVm requestOrder(
            RequestOrderVm requestOrderVm
    );

    OrderResponseVm getOrdersForLoggedInUser(
            int page,
            int size
    );

    OrderResponseVm getAllOrders(
            int page,
            int size
    );

    OrderResponseVm searchOrders(
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