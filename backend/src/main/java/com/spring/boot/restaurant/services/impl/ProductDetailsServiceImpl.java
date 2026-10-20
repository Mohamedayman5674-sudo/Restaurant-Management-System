package com.spring.boot.restaurant.services.impl;

import com.spring.boot.restaurant.controllers.vm.ProductDetailsResponseVm;
import com.spring.boot.restaurant.dto.ProductDetailsDto;
import com.spring.boot.restaurant.mappers.ProductDetailsMapper;
import com.spring.boot.restaurant.models.Product;
import com.spring.boot.restaurant.models.ProductDetails;
import com.spring.boot.restaurant.repositories.ProductDetailsRepo;
import com.spring.boot.restaurant.repositories.ProductRepo;
import com.spring.boot.restaurant.services.ProductDetailsService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProductDetailsServiceImpl
        implements ProductDetailsService {

    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    @Cacheable(
            value = "productDetails",
            key = "'all-' + #page + '-' + #size"
    )
    public ProductDetailsResponseVm getAllProductDetails(
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

            Page<ProductDetails> productDetailsPage =
                    productDetailsRepo.findAll(pageable);

            if (productDetailsPage.isEmpty()) {

                throw new SystemException(
                        "product_details.not.found"
                );
            }

            return new ProductDetailsResponseVm(

                    productDetailsPage
                            .getContent()
                            .stream()
                            .map(
                                    ProductDetailsMapper
                                            .PRODUCT_DETAILS_MAPPER
                                            ::toProductDetailsDto
                            )
                            .toList(),

                    productDetailsPage.getTotalElements()
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

    @Override
    @CacheEvict(
            value = "productDetails",
            allEntries = true
    )
    public ProductDetailsDto addProductDetails(
            ProductDetailsDto productDetailsDto) {

        try {

            if (Objects.isNull(productDetailsDto)) {

                throw new SystemException(
                        "error.product_details.not_null"
                );
            }

            if (Objects.nonNull(productDetailsDto.getId())) {

                throw new SystemException(
                        "id.must_be.null"
                );
            }

            if (Objects.isNull(
                    productDetailsDto.getProductId())) {

                throw new SystemException(
                        "id.must_be.not_null"
                );
            }

            Optional<Product> product =
                    productRepo.findById(
                            productDetailsDto.getProductId()
                    );

            if (product.isEmpty()) {

                throw new SystemException(
                        "products.not.found"
                );
            }

            if (productDetailsRepo.existsByProductId(
                    productDetailsDto.getProductId())) {

                throw new SystemException(
                        "product_details.already.exists"
                );
            }

            ProductDetails productDetails =
                    new ProductDetails();

            productDetails.setProduct(
                    product.get()
            );

            productDetails =
                    productDetailsRepo.save(
                            productDetails
                    );

            return ProductDetailsMapper
                    .PRODUCT_DETAILS_MAPPER
                    .toProductDetailsDto(
                            productDetails
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

    @Override
    @CacheEvict(
            value = "productDetails",
            allEntries = true
    )
    public ProductDetailsDto updateProductDetails(
            ProductDetailsDto productDetailsDto) {

        try {

            if (Objects.isNull(productDetailsDto)
                    || Objects.isNull(
                    productDetailsDto.getId())) {

                throw new SystemException(
                        "id.must_be.not_null"
                );
            }

            if (Objects.isNull(
                    productDetailsDto.getProductId())) {

                throw new SystemException(
                        "id.must_be.not_null"
                );
            }

            Optional<ProductDetails> existingDetails =
                    productDetailsRepo.findById(
                            productDetailsDto.getId()
                    );

            if (existingDetails.isEmpty()) {

                throw new SystemException(
                        "product_details.not.found"
                );
            }

            Optional<Product> product =
                    productRepo.findById(
                            productDetailsDto.getProductId()
                    );

            if (product.isEmpty()) {

                throw new SystemException(
                        "products.not.found"
                );
            }

            if (productDetailsRepo
                    .existsByProductIdAndIdNot(
                            productDetailsDto.getProductId(),
                            productDetailsDto.getId()
                    )) {

                throw new SystemException(
                        "product_details.already.exists"
                );
            }

            ProductDetails productDetails =
                    existingDetails.get();

            productDetails.setProduct(
                    product.get()
            );

            productDetails =
                    productDetailsRepo.save(
                            productDetails
                    );

            return ProductDetailsMapper
                    .PRODUCT_DETAILS_MAPPER
                    .toProductDetailsDto(
                            productDetails
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

    @Override
    @CacheEvict(
            value = "productDetails",
            allEntries = true
    )
    public void deleteProductDetails(Long id) {

        try {

            if (Objects.isNull(id)) {

                throw new SystemException(
                        "id.must_be.not_null"
                );
            }

            if (!productDetailsRepo.existsById(id)) {

                throw new SystemException(
                        "product_details.not.found"
                );
            }

            productDetailsRepo.deleteProductDetailsById(id);

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