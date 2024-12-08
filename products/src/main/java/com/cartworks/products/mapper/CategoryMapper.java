package com.cartworks.products.mapper;

import com.cartworks.products.dto.CategoryDto;
import com.cartworks.products.entity.Category;

import java.util.stream.Collectors;

public class CategoryMapper {

    // Convert Category entity to CategoryDTO
    public static CategoryDto mapToCategoryDto(Category category, CategoryDto categoryDTO) {
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setProductIds(category.getProducts() != null
                ? category.getProducts().stream().map(product -> product.getId()).collect(Collectors.toList())
                : null);
        return categoryDTO;
    }

    // Convert CategoryDTO to Category entity
    public static Category mapToCategory(CategoryDto categoryDTO, Category category) {
        category.setName(categoryDTO.getName());
        // Product list is managed elsewhere (e.g., service layer)
        return category;
    }
}
