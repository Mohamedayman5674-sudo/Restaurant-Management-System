package com.spring.boot.restaurant.services.impl;

import com.spring.boot.restaurant.controllers.vm.ProductResponseVm;
import com.spring.boot.restaurant.dto.ProductDto;
import com.spring.boot.restaurant.mappers.ProductMapper;
import com.spring.boot.restaurant.models.Product;
import com.spring.boot.restaurant.repositories.CategoryRepo;
import com.spring.boot.restaurant.repositories.ProductRepo;
import com.spring.boot.restaurant.services.CategoryService;
import com.spring.boot.restaurant.services.ProductService;
import com.spring.boot.restaurant.specifications.ProductSpecification;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryService categoryService;


    @Override
    @Cacheable(
            value = "products",
            key = "'all-' + #page + '-' + #size"
    )
    public ProductResponseVm getAllProducts(
            int page,
            int size) {

        try {

            Pageable pageable =
                    getPageable(page, size);

            Page<Product> result =
                    productRepo.findAllByOrderByIdAsc(
                            pageable
                    );

            if (result.isEmpty()) {

                throw new SystemException(
                        "products.not.found"
                );
            }

            return new ProductResponseVm(
                    result.getContent()
                            .stream()
                            .map(
                                    ProductMapper
                                            .PRODUCT_MAPPER
                                            ::toProductDto
                            )
                            .toList(),
                    result.getTotalElements()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    @Override
    @Cacheable(
            value = "products",
            key = "'category-' + #id + '-' + #page + '-' + #size"
    )
    public ProductResponseVm getAllProductsByCategoryId(
            Long id,
            int page,
            int size) {

        try {

            if (Objects.isNull(id)) {

                throw new SystemException(
                        "id.must_be.not_null"
                );
            }

            categoryService.getCategoryById(id);

            Pageable pageable =
                    getPageable(page, size);

            Page<Product> result =
                    productRepo
                            .findAllProductsByCategoryIdByOrderByIdAsc(
                                    id,
                                    pageable
                            );

            if (result.isEmpty()) {

                throw new SystemException(
                        "products.category.not.found"
                );
            }

            return new ProductResponseVm(
                    result.getContent()
                            .stream()
                            .map(
                                    ProductMapper
                                            .PRODUCT_MAPPER
                                            ::toProductDto
                            )
                            .toList(),
                    result.getTotalElements()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    @Override
    @CacheEvict(
            value = {"products", "productSearch"},
            allEntries = true
    )
    public ProductDto createProduct(
            ProductDto productDto) {

        try {

            if (Objects.nonNull(
                    productDto.getId())) {

                throw new SystemException(
                        "id.must_be.null"
                );
            }

            Product product =
                    ProductMapper
                            .PRODUCT_MAPPER
                            .toProduct(productDto);

            product =
                    productRepo.save(product);

            return ProductMapper
                    .PRODUCT_MAPPER
                    .toProductDto(product);

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    @Override
    @CacheEvict(
            value = {"products", "productSearch"},
            allEntries = true
    )
    public List<ProductDto> createListOfProduct(
            List<ProductDto> productDtos) {

        try {

            if (Objects.isNull(productDtos)
                    || productDtos.isEmpty()) {

                throw new SystemException(
                        "error.empty.list.product"
                );
            }

            for (ProductDto productDto :
                    productDtos) {

                if (Objects.nonNull(
                        productDto.getId())) {

                    throw new SystemException(
                            "id.must_be.null"
                    );
                }
            }

            List<Product> products =
                    productDtos
                            .stream()
                            .map(
                                    ProductMapper
                                            .PRODUCT_MAPPER
                                            ::toProduct
                            )
                            .toList();

            products =
                    productRepo.saveAll(
                            products
                    );

            return products
                    .stream()
                    .map(
                            ProductMapper
                                    .PRODUCT_MAPPER
                                    ::toProductDto
                    )
                    .toList();

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    // =========================================================
    // UPDATE PRODUCT
    // =========================================================

    @Override
    @CacheEvict(value={"products","productSearch"}, allEntries=true)
    public ProductDto updateProduct(ProductDto productDto) {
        try {

            if (Objects.isNull(productDto.getId())) {
                throw new SystemException("id.must_be.not_null");
            }

            Optional<Product> existingProduct =
                    productRepo.findById(productDto.getId());

            if (existingProduct.isEmpty()) {
                throw new SystemException("products.not.found");
            }

            Product product = existingProduct.get();

            // Update normal product data
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setImagePath(productDto.getImagePath());

            // Change category ONLY if the user selected a new category
            if (Objects.nonNull(productDto.getCategory())
                    && Objects.nonNull(productDto.getCategory().getId())) {

                var category =
                        categoryRepo.findById(productDto.getCategory().getId());

                if (category.isEmpty()) {
                    throw new SystemException("category.not.found");
                }

                product.setCategory(category.get());
            }

            product = productRepo.save(product);

            return ProductMapper.PRODUCT_MAPPER.toProductDto(product);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage())
                        ;
        }
    }


    @Override
    @CacheEvict(
            value = {"products", "productSearch"},
            allEntries = true
    )
    public List<ProductDto> updateListOfProduct(
            List<ProductDto> productDtos) {

        try {

            if (Objects.isNull(productDtos)
                    || productDtos.isEmpty()) {

                throw new SystemException(
                        "error.empty.list.product"
                );
            }

            for (ProductDto productDto :
                    productDtos) {

                if (Objects.isNull(
                        productDto.getId())) {

                    throw new SystemException(
                            "id.must_be.not_null"
                    );
                }

                if (!productRepo.existsById(
                        productDto.getId())) {

                    throw new SystemException(
                            "products.not.found"
                    );
                }
            }

            List<Product> products =
                    productDtos
                            .stream()
                            .map(
                                    ProductMapper
                                            .PRODUCT_MAPPER
                                            ::toProduct
                            )
                            .toList();

            products =
                    productRepo.saveAll(
                            products
                    );

            return products
                    .stream()
                    .map(
                            ProductMapper
                                    .PRODUCT_MAPPER
                                    ::toProductDto
                    )
                    .toList();

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    @Override
    @CacheEvict(
            value = {"products", "productSearch"},
            allEntries = true
    )
    public void deleteProductById(
            Long id) {

        try {

            if (Objects.isNull(id)) {

                throw new SystemException(
                        "id.must_be.not_null"
                );
            }

            if (!productRepo.existsById(id)) {

                throw new SystemException(
                        "products.not.found"
                );
            }

            productRepo.deleteById(id);

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    @Override
    @CacheEvict(
            value = {"products", "productSearch"},
            allEntries = true
    )
    public void deleteListOfProduct(
            List<Long> productIds) {

        try {

            if (Objects.isNull(productIds)
                    || productIds.isEmpty()) {

                throw new SystemException(
                        "error.empty.list.product"
                );
            }

            for (Long id : productIds) {

                if (Objects.isNull(id)) {

                    throw new SystemException(
                            "id.must_be.not_null"
                    );
                }

                if (!productRepo.existsById(id)) {

                    throw new SystemException(
                            "products.not.found"
                    );
                }
            }

            productRepo.deleteAllById(
                    productIds
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    @Override
    @Cacheable(
            value = "products",
            key = "'id-' + #id"
    )
    public ProductDto getProductById(
            Long id) {

        try {

            if (Objects.isNull(id)) {

                throw new SystemException(
                        "id.must_be.not_null"
                );
            }

            Optional<Product> result =
                    productRepo.findById(id);

            if (result.isEmpty()) {

                throw new SystemException(
                        "products.not.found"
                );
            }

            return ProductMapper
                    .PRODUCT_MAPPER
                    .toProductDto(
                            result.get()
                    );

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    @Override
    @Cacheable(
            value = "products",
            key = "'key-' + #key + '-' + #page + '-' + #size"
    )
    public ProductResponseVm getAllProductsByKey(
            String key,
            int page,
            int size) {

        try {

            if (Objects.isNull(key)
                    || key.trim().isEmpty()) {

                throw new SystemException(
                        "key.null"
                );
            }

            Pageable pageable =
                    getPageable(page, size);

            Page<Product> result =
                    productRepo
                            .getAllProductsByKeyByOrderByIdAsc(
                                    key,
                                    pageable
                            );

            if (result.isEmpty()) {

                throw new SystemException(
                        "products.not.found"
                );
            }

            return new ProductResponseVm(
                    result.getContent()
                            .stream()
                            .map(
                                    ProductMapper
                                            .PRODUCT_MAPPER
                                            ::toProductDto
                            )
                            .toList(),
                    result.getTotalElements()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    @Override
    @Cacheable(
            value = "products",
            key = "'category-key-' + #categoryId + '-' + #key + '-' + #page + '-' + #size"
    )
    public ProductResponseVm getAllProductsByCategoryIdAndKey(
            Long categoryId,
            String key,
            int page,
            int size) {

        try {

            if (Objects.isNull(categoryId)) {

                throw new SystemException(
                        "id.must_be.not_null"
                );
            }

            if (Objects.isNull(key)
                    || key.trim().isEmpty()) {

                throw new SystemException(
                        "key.null"
                );
            }

            categoryService.getCategoryById(
                    categoryId
            );

            Pageable pageable =
                    getPageable(page, size);

            Page<Product> result =
                    productRepo
                            .getAllProductsByKeyByCategoryIdByOrderByIdAsc(
                                    categoryId,
                                    key,
                                    pageable
                            );

            if (result.isEmpty()) {

                throw new SystemException(
                        "products.not.found"
                );
            }

            return new ProductResponseVm(
                    result.getContent()
                            .stream()
                            .map(
                                    ProductMapper
                                            .PRODUCT_MAPPER
                                            ::toProductDto
                            )
                            .toList(),
                    result.getTotalElements()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    @Override
    @Cacheable(
            value = "productSearch",
            key = "#createdFrom + '-' + #createdTo + '-' + #updatedFrom + '-' + #updatedTo + '-' + #createdBy + '-' + #updatedBy + '-' + #page + '-' + #size"
    )
    public ProductResponseVm searchProducts(
            LocalDateTime createdFrom,
            LocalDateTime createdTo,
            LocalDateTime updatedFrom,
            LocalDateTime updatedTo,
            String createdBy,
            String updatedBy,
            int page,
            int size) {

        try {

            Pageable pageable =
                    getPageable(page, size);

            Specification<Product> specification =
                    ProductSpecification.search(
                            createdFrom,
                            createdTo,
                            updatedFrom,
                            updatedTo,
                            createdBy,
                            updatedBy
                    );

            Page<Product> result =
                    productRepo.findAll(
                            specification,
                            pageable
                    );

            if (result.isEmpty()) {

                throw new SystemException(
                        "products.not.found"
                );
            }

            return new ProductResponseVm(
                    result.getContent()
                            .stream()
                            .map(
                                    ProductMapper
                                            .PRODUCT_MAPPER
                                            ::toProductDto
                            )
                            .toList(),
                    result.getTotalElements()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    private static Pageable getPageable(
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

            return PageRequest.of(
                    page - 1,
                    size
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    @Override
    @Cacheable(
            value = "products",
            key = "'ids-' + #ids"
    )
    public List<ProductDto> getProductsByIds(
            List<Long> ids) {

        if (Objects.isNull(ids)
                || ids.isEmpty()) {

            return List.of();
        }

        return ProductMapper
                .PRODUCT_MAPPER
                .toDtoList(
                        productRepo.findAllById(ids)
                );
    }
}