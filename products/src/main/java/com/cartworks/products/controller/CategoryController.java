package com.cartworks.products.controller;

import com.cartworks.products.dto.ResponseDto;
import com.cartworks.products.entity.Category;
import com.cartworks.products.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/categories")
@Tag(
        name = "Category Management API",
        description = "CRUD operations for managing product categories"
)
@Validated
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get All Categories", description = "Fetch all available categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    @GetMapping
    public ResponseEntity<ResponseDto<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>("200", "Categories retrieved successfully", categories));
    }

    @Operation(summary = "Get Category By ID", description = "Fetch a category by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Category>> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id); // Throws ResourceNotFoundException if not found
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>("200", "Category retrieved successfully", category));
    }

    @Operation(summary = "Create a New Category", description = "Create a new category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(schema = @Schema(implementation = Category.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseDto<Category>> createCategory(@Valid @RequestBody Category category) {
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto<>("201", "Category created successfully", savedCategory));
    }

    @Operation(summary = "Update Category", description = "Update an existing category by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated successfully",
                    content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Category>> updateCategory(@PathVariable Long id, @Valid @RequestBody Category updatedCategory) {
        Category existingCategory = categoryService.getCategoryById(id);
        existingCategory.setName(updatedCategory.getName());
        Category savedCategory = categoryService.saveCategory(existingCategory);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>("200", "Category updated successfully", savedCategory));
    }

    @Operation(summary = "Delete Category", description = "Delete a category by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
