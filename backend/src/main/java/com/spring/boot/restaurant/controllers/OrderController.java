package com.spring.boot.restaurant.controllers;

import com.spring.boot.restaurant.controllers.vm.OrderResponseVm;
import com.spring.boot.restaurant.controllers.vm.RequestOrderVm;
import com.spring.boot.restaurant.controllers.vm.ResponseOrderVm;
import com.spring.boot.restaurant.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<ResponseOrderVm> createOrder(
            @RequestBody @Valid RequestOrderVm orders) {

        return ResponseEntity
                .created(
                        URI.create("/order/create-order")
                )
                .body(
                        orderService.requestOrder(orders)
                );
    }

    @GetMapping("/my-orders")
    public ResponseEntity<OrderResponseVm> getMyOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(
                orderService.getOrdersForLoggedInUser(
                        page,
                        size
                )
        );
    }

    @GetMapping("/all-orders")
    public ResponseEntity<OrderResponseVm> getAllOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(
                orderService.getAllOrders(
                        page,
                        size
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<OrderResponseVm> searchOrders(

            @RequestParam(required = false)
            LocalDateTime createdFrom,

            @RequestParam(required = false)
            LocalDateTime createdTo,

            @RequestParam(required = false)
            LocalDateTime updatedFrom,

            @RequestParam(required = false)
            LocalDateTime updatedTo,

            @RequestParam(required = false)
            String createdBy,

            @RequestParam(required = false)
            String updatedBy,

            @RequestParam(defaultValue = "1")
            int page,

            @RequestParam(defaultValue = "5")
            int size) {

        return ResponseEntity.ok(
                orderService.searchOrders(
                        createdFrom,
                        createdTo,
                        updatedFrom,
                        updatedTo,
                        createdBy,
                        updatedBy,
                        page,
                        size
                )
        );
    }
}