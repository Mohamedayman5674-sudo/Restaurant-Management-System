package com.spring.boot.restaurant.controllers;

import com.spring.boot.restaurant.controllers.vm.ProductResponseVm;
import com.spring.boot.restaurant.dto.ProductDto;
import com.spring.boot.restaurant.services.ProductService;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Get all products
    @GetMapping("/all-products")
    public ResponseEntity<ProductResponseVm> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) throws SystemException {

        return ResponseEntity.ok(
                productService.getAllProducts(page, size)
        );
    }

    // Get all products by category id
    @GetMapping("/all-products/{id}")
    public ResponseEntity<ProductResponseVm> getAllProductsByCategoryId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) throws SystemException {

        return ResponseEntity.ok(
                productService.getAllProductsByCategoryId(
                        id,
                        page,
                        size
                )
        );
    }

    // Get products by key
    @GetMapping("/all-products-by-key")
    public ResponseEntity<ProductResponseVm> getAllProductsByKey(
            @RequestParam String key,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) throws SystemException {

        return ResponseEntity.ok(
                productService.getAllProductsByKey(
                        key,
                        page,
                        size
                )
        );
    }

    // Get products by key and category id
    @GetMapping("/all-products-by-key-and-category-id")
    public ResponseEntity<ProductResponseVm> getAllProductsByKeyAndCategoryId(
            @RequestParam Long categoryId,
            @RequestParam String key,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) throws SystemException {

        return ResponseEntity.ok(
                productService.getAllProductsByCategoryIdAndKey(
                        categoryId,
                        key,
                        page,
                        size
                )
        );
    }

    // Create product
    @PostMapping("/create-product")
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody @Valid ProductDto productDto) throws SystemException {

        return ResponseEntity.ok(
                productService.createProduct(productDto)
        );
    }

    // Create list of products
    @PostMapping("/create-products")
    public ResponseEntity<List<ProductDto>> createProducts(
            @RequestBody @Valid List<ProductDto> products) throws SystemException {

        return ResponseEntity.ok(
                productService.createListOfProduct(products)
        );
    }

    // Update product
    @PutMapping("/update-product")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestBody @Valid ProductDto productDto) throws SystemException {

        return ResponseEntity.ok(
                productService.updateProduct(productDto)
        );
    }

    // Update list of products
    @PutMapping("/update-products")
    public ResponseEntity<List<ProductDto>> updateProducts(
            @RequestBody @Valid List<ProductDto> products) throws SystemException {

        return ResponseEntity.ok(
                productService.updateListOfProduct(products)
        );
    }

    // Delete product by id
    @DeleteMapping("/delete-product")
    public ResponseEntity<Void> deleteProduct(
            @RequestParam Long id) throws SystemException {

        productService.deleteProductById(id);

        return ResponseEntity.noContent().build();
    }

    // Delete list of products
    @DeleteMapping("/delete-products")
    public ResponseEntity<Void> deleteProducts(
            @RequestParam List<Long> ids) throws SystemException {

        productService.deleteListOfProduct(ids);

        return ResponseEntity.noContent().build();
    }

    // Search products by audit fields
    @GetMapping("/search")
    public ResponseEntity<ProductResponseVm> searchProducts(
            @RequestParam(required = false) LocalDateTime createdFrom,
            @RequestParam(required = false) LocalDateTime createdTo,
            @RequestParam(required = false) LocalDateTime updatedFrom,
            @RequestParam(required = false) LocalDateTime updatedTo,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String updatedBy,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) throws SystemException {

        return ResponseEntity.ok(
                productService.searchProducts(
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