package com.spring.boot.restaurant.services.impl;

import com.spring.boot.restaurant.controllers.vm.CategoryResponseVm;
import com.spring.boot.restaurant.dto.CategoryDto;
import com.spring.boot.restaurant.mappers.CategoryMapper;
import com.spring.boot.restaurant.models.Category;
import com.spring.boot.restaurant.repositories.CategoryRepo;
import com.spring.boot.restaurant.services.CategoryService;
import com.spring.boot.restaurant.specifications.CategorySpecification;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;


    // =========================
    // GET ALL CATEGORIES
    // WITH PAGINATION
    // =========================

    @Override
    @Cacheable(
            value = "categories",
            key = "'all-' + #page + '-' + #size"
    )
    public CategoryResponseVm getAllCategories(
            int page,
            int size) {

        try {

            if (page < 1) {
                throw new SystemException(
                        "page.must_be.greater.than.zero"
                );
            }

            if (size <= 0) {
                throw new SystemException(
                        "size.must_be.greater.than.zero"
                );
            }

            Pageable pageable =
                    PageRequest.of(page - 1, size);

            Page<Category> categories =
                    categoryRepo.findAllByOrderByNameAsc(pageable);

            if (categories.isEmpty()) {
                throw new SystemException(
                        "error.empty.list.category"
                );
            }

            List<CategoryDto> categoryDtos =
                    categories.map(
                            CategoryMapper.CATEGORY_MAPPER::toCategoryDto
                    ).getContent();

            return new CategoryResponseVm(
                    categoryDtos,
                    categories.getTotalElements()
            );

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    // =========================
    // CREATE CATEGORY
    // =========================

    @Override
    @CacheEvict(
            value = {"categories", "categorySearch"},
            allEntries = true
    )
    public CategoryDto createCategory(
            CategoryDto categoryDto) {

        try {

            if (Objects.nonNull(categoryDto.getId())) {
                throw new SystemException(
                        "id.must_be.null"
                );
            }

            Category category =
                    CategoryMapper.CATEGORY_MAPPER
                            .toCategory(categoryDto);

            category = categoryRepo.save(category);

            return CategoryMapper.CATEGORY_MAPPER
                    .toCategoryDto(category);

        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
    }


    // =========================
    // CREATE LIST OF CATEGORIES
    // =========================

    @Override
    @CacheEvict(
            value = {"categories", "categorySearch"},
            allEntries = true
    )
    public List<CategoryDto> createListOfCategory(
            List<CategoryDto> categoriesDto) {

        try {

            if (Objects.isNull(categoriesDto)
                    || categoriesDto.isEmpty()) {

                throw new SystemException(
                        "error.empty.list.category"
                );
            }

            for (CategoryDto categoryDto : categoriesDto) {

                if (Objects.nonNull(categoryDto.getId())) {

                    throw new SystemException(
                            "id.must_be.null"
                    );
                }
            }

            List<Category> categories =
                    categoriesDto.stream()
                            .map(
                                    CategoryMapper.CATEGORY_MAPPER
                                            ::toCategory
                            )
                            .toList();

            categories = categoryRepo.saveAll(categories);

            return categories.stream()
                    .map(
                            CategoryMapper.CATEGORY_MAPPER
                                    ::toCategoryDto
                    )
                    .toList();

        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
    }


    // =========================
    // UPDATE CATEGORY
    // =========================

    @Override
    @CacheEvict(
            value = {"categories", "categorySearch"},
            allEntries = true
    )
    public CategoryDto updateCategory(
            CategoryDto categoryDto) {

        try {

            if (Objects.isNull(categoryDto.getId())) {

                throw new SystemException(
                        "id.must_be.not_null"
                );
            }

            Optional<Category> existingCategory =
                    categoryRepo.findById(categoryDto.getId());

            if (existingCategory.isEmpty()) {

                throw new SystemException(
                        "category.not.found"
                );
            }

            Category category =
                    CategoryMapper.CATEGORY_MAPPER
                            .toCategory(categoryDto);

            category = categoryRepo.save(category);

            return CategoryMapper.CATEGORY_MAPPER
                    .toCategoryDto(category);

        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
    }


    // =========================
    // UPDATE LIST OF CATEGORIES
    // =========================

    @Override
    @CacheEvict(
            value = {"categories", "categorySearch"},
            allEntries = true
    )
    public List<CategoryDto> updateListOfCategory(
            List<CategoryDto> categoriesDto) {

        try {

            if (Objects.isNull(categoriesDto)
                    || categoriesDto.isEmpty()) {

                throw new SystemException(
                        "error.empty.list.category"
                );
            }

            for (CategoryDto categoryDto : categoriesDto) {

                if (Objects.isNull(categoryDto.getId())) {

                    throw new SystemException(
                            "id.must_be.not_null"
                    );
                }

                if (!categoryRepo.existsById(
                        categoryDto.getId())) {

                    throw new SystemException(
                            "category.not.found"
                    );
                }
            }

            List<Category> categories =
                    categoriesDto.stream()
                            .map(
                                    CategoryMapper.CATEGORY_MAPPER
                                            ::toCategory
                            )
                            .toList();

            categories = categoryRepo.saveAll(categories);

            return categories.stream()
                    .map(
                            CategoryMapper.CATEGORY_MAPPER
                                    ::toCategoryDto
                    )
                    .toList();

        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
    }


    // =========================
    // DELETE CATEGORY BY ID
    // =========================

    @Override
    @CacheEvict(
            value = {"categories", "categorySearch"},
            allEntries = true
    )
    public void deleteCategoryById(Long id) {

        try {

            if (Objects.isNull(id)) {

                throw new SystemException(
                        "id.must_be.not_null"
                );
            }

            if (!categoryRepo.existsById(id)) {

                throw new SystemException(
                        "category.not.found"
                );
            }

            categoryRepo.deleteById(id);

        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
    }


    // =========================
    // DELETE LIST OF CATEGORIES
    // =========================

    @Override
    @CacheEvict(
            value = {"categories", "categorySearch"},
            allEntries = true
    )
    public void deleteListOfCategory(
            List<Long> categoryIds) {

        try {

            if (Objects.isNull(categoryIds)
                    || categoryIds.isEmpty()) {

                throw new SystemException(
                        "error.empty.list.category"
                );
            }

            for (Long id : categoryIds) {

                if (Objects.isNull(id)) {

                    throw new SystemException(
                            "id.must_be.not_null"
                    );
                }

                if (!categoryRepo.existsById(id)) {

                    throw new SystemException(
                            "category.not.found"
                    );
                }
            }

            categoryRepo.deleteAllById(categoryIds);

        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
    }


    // =========================
    // SEARCH CATEGORIES
    // =========================

    @Override
    @Cacheable(
            value = "categorySearch",
            key = "#createdFrom + '-' + #createdTo + '-' + #updatedFrom + '-' + #updatedTo + '-' + #createdBy + '-' + #updatedBy + '-' + #page + '-' + #size"
    )
    public CategoryResponseVm searchCategories(
            LocalDateTime createdFrom,
            LocalDateTime createdTo,
            LocalDateTime updatedFrom,
            LocalDateTime updatedTo,
            String createdBy,
            String updatedBy,
            int page,
            int size) {

        try {

            if (page < 1) {

                throw new SystemException(
                        "page.must_be.greater.than.zero"
                );
            }

            if (size <= 0) {

                throw new SystemException(
                        "size.must_be.greater.than.zero"
                );
            }

            Pageable pageable =
                    PageRequest.of(
                            page - 1,
                            size
                    );

            Specification<Category> specification =
                    CategorySpecification.search(
                            createdFrom,
                            createdTo,
                            updatedFrom,
                            updatedTo,
                            createdBy,
                            updatedBy
                    );

            Page<Category> categories =
                    categoryRepo.findAll(
                            specification,
                            pageable
                    );

            if (categories.isEmpty()) {

                throw new SystemException(
                        "error.empty.list.category"
                );
            }

            List<CategoryDto> categoryDtos =
                    categories.getContent()
                            .stream()
                            .map(
                                    CategoryMapper
                                            .CATEGORY_MAPPER
                                            ::toCategoryDto
                            )
                            .toList();

            return new CategoryResponseVm(
                    categoryDtos,
                    categories.getTotalElements()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    // =========================
    // GET CATEGORY BY ID
    // =========================

    @Override
    @Cacheable(
            value = "categories",
            key = "'id-' + #id"
    )
    public CategoryDto getCategoryById(Long id) {

        try {

            if (Objects.isNull(id)) {

                throw new SystemException(
                        "id.must_be.not_null"
                );
            }

            Optional<Category> result =
                    categoryRepo.findById(id);

            if (result.isEmpty()) {

                throw new SystemException(
                        "category.not.found"
                );
            }

            return CategoryMapper.CATEGORY_MAPPER
                    .toCategoryDto(result.get());

        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
    }
}