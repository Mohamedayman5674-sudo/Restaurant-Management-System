package com.spring.boot.restaurant.controllers;

import com.spring.boot.restaurant.controllers.vm.ProductDetailsResponseVm;
import com.spring.boot.restaurant.dto.ProductDetailsDto;
import com.spring.boot.restaurant.services.ProductDetailsService;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-details")
public class ProductDetailsController {

    @Autowired
    private ProductDetailsService productDetailsService;

    @GetMapping("/all-product-details")
    public ResponseEntity<ProductDetailsResponseVm> getAllProductDetails(
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                productDetailsService.getAllProductDetails(
                        page,
                        size
                )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDetailsDto> addProductDetails(
            @RequestBody @Valid ProductDetailsDto productDetailsDto
    ) throws SystemException {

        return ResponseEntity.ok(
                productDetailsService.addProductDetails(
                        productDetailsDto
                )
        );
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDetailsDto> updateProductDetails(
            @RequestBody @Valid ProductDetailsDto productDetailsDto
    ) throws SystemException {

        return ResponseEntity.ok(
                productDetailsService.updateProductDetails(
                        productDetailsDto
                )
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProductDetails(
            @RequestParam Long id
    ) throws SystemException {

        productDetailsService.deleteProductDetails(id);

        return ResponseEntity.noContent().build();
    }
}