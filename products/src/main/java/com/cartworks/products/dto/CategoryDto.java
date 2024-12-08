package com.cartworks.products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {

    private Long id; // No validation needed as it’s typically auto-generated.

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters")
    private String name;

    // Optional: List of product IDs under the category.
    private List<Long> productIds;


}
