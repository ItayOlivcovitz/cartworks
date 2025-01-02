package com.cartworks.products.mapper;

import com.cartworks.products.dto.ProductsDto;
import com.cartworks.products.entity.Category;
import com.cartworks.products.entity.Product;
import com.cartworks.products.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryRepository categoryRepository;

    // Convert Product entity to ProductDto
    public ProductsDto mapToProductDto(Product product) {
        ProductsDto productDto = new ProductsDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setWeight(product.getWeight());
        productDto.setDimensions(product.getDimensions());
        productDto.setIsAvailable(product.getIsAvailable());
        productDto.setColor(product.getColor());
        productDto.setBrand(product.getBrand());
        productDto.setMaterial(product.getMaterial());
        productDto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        return productDto;
    }

    // Convert ProductDto to Product entity
    public Product mapToProduct(ProductsDto productDto, Product product) {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setImageUrl(productDto.getImageUrl());
        product.setWeight(productDto.getWeight());
        product.setDimensions(productDto.getDimensions());
        product.setIsAvailable(productDto.getIsAvailable());
        product.setColor(productDto.getColor());
        product.setBrand(productDto.getBrand());
        product.setMaterial(productDto.getMaterial());

        // Convert categoryId to Category entity
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with ID " + productDto.getCategoryId()));
            product.setCategory(category);
        } else {
            product.setCategory(null); // Handle null category
        }

        return product;
    }
}
