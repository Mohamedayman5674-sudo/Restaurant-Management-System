    package com.spring.boot.restaurant.controllers;

    import com.spring.boot.restaurant.controllers.vm.CategoryResponseVm;
    import com.spring.boot.restaurant.dto.CategoryDto;
    import com.spring.boot.restaurant.services.CategoryService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.transaction.SystemException;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.time.LocalDateTime;
    import java.util.List;

    @Tag(
            name = "Category Controller",
            description = "Category APIs"
    )
    @RestController
    @RequestMapping("/categories")
    public class CategoryController {

        @Autowired
        private CategoryService categoryService;


        @Operation(summary = "get all categories with pagination")
        @GetMapping("/all-categories")
        public ResponseEntity<CategoryResponseVm> getAllCategories(
                @RequestParam(defaultValue = "1") int page,
                @RequestParam(defaultValue = "5") int size
        ) throws SystemException {

            return ResponseEntity.ok(
                    categoryService.getAllCategories(page, size)
            );
        }


        @Operation(summary = "create category")
        @PostMapping("/create-category")
        public ResponseEntity<CategoryDto> createCategory(
                @RequestBody @Valid CategoryDto categoryDto) throws SystemException {

            return ResponseEntity.ok(
                    categoryService.createCategory(categoryDto)
            );
        }


        @Operation(summary = "create list of categories")
        @PostMapping("/create-categories")
        public ResponseEntity<List<CategoryDto>> createCategories(
                @RequestBody @Valid List<CategoryDto> categories) throws SystemException {

            return ResponseEntity.ok(
                    categoryService.createListOfCategory(categories)
            );
        }


        @Operation(summary = "update category")
        @PutMapping("/update-category")
        public ResponseEntity<CategoryDto> updateCategory(
                @RequestBody @Valid CategoryDto categoryDto) throws SystemException {

            return ResponseEntity.ok(
                    categoryService.updateCategory(categoryDto)
            );
        }


        @Operation(summary = "update list of categories")
        @PutMapping("/update-categories")
        public ResponseEntity<List<CategoryDto>> updateCategories(
                @RequestBody @Valid List<CategoryDto> categories) throws SystemException {

            return ResponseEntity.ok(
                    categoryService.updateListOfCategory(categories)
            );
        }


        @Operation(summary = "delete category by id")
        @DeleteMapping("/delete-category")
        public ResponseEntity<Void> deleteCategory(
                @RequestParam Long id) throws SystemException {

            categoryService.deleteCategoryById(id);

            return ResponseEntity.noContent().build();
        }


        @Operation(summary = "delete list of categories")
        @DeleteMapping("/delete-categories")
        public ResponseEntity<Void> deleteCategories(
                @RequestParam List<Long> ids) throws SystemException {

            categoryService.deleteListOfCategory(ids);

            return ResponseEntity.noContent().build();
        }


        @Operation(summary = "search categories by audit fields")
        @GetMapping("/search")
        public ResponseEntity<CategoryResponseVm> searchCategories(
                @RequestParam(required = false) LocalDateTime createdFrom,
                @RequestParam(required = false) LocalDateTime createdTo,
                @RequestParam(required = false) LocalDateTime updatedFrom,
                @RequestParam(required = false) LocalDateTime updatedTo,
                @RequestParam(required = false) String createdBy,
                @RequestParam(required = false) String updatedBy,
                @RequestParam(defaultValue = "1") int page,
                @RequestParam(defaultValue = "5") int size
        ) throws SystemException {

            return ResponseEntity.ok(
                    categoryService.searchCategories(
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


        @Operation(summary = "get category by id")
        @GetMapping("/get-category/{id}")
        public ResponseEntity<CategoryDto> getCategoryById(
                @PathVariable Long id) throws SystemException {

            return ResponseEntity.ok(
                    categoryService.getCategoryById(id)
            );
        }
    }