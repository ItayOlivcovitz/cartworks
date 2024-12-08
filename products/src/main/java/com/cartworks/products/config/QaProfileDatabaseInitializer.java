package com.cartworks.products.config;

import com.cartworks.products.entity.Category;
import com.cartworks.products.entity.Product;
import com.cartworks.products.repository.CategoryRepository;
import com.cartworks.products.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("qa")
public class QaProfileDatabaseInitializer {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public QaProfileDatabaseInitializer(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void init() {
        // Create and save categories
        List<Category> categories = List.of(
                Category.builder().name("Electronics").build(),
                Category.builder().name("Furniture").build(),
                Category.builder().name("Clothing").build(),
                Category.builder().name("Books").build()
        );

        categoryRepository.saveAll(categories);

        // Create and save products for each category
        categories.forEach(category -> {
            List<Product> products = List.of(
                    createProduct("Product 1 for " + category.getName(), category),
                    createProduct("Product 2 for " + category.getName(), category),
                    createProduct("Product 3 for " + category.getName(), category)
            );
            productRepository.saveAll(products);
        });
    }

    private Product createProduct(String name, Category category) {
        return Product.builder()
                .name(name)
                .description("Description of " + name)
                .price(100.0)
                .stock(10)
                .isAvailable(true)
                .weight(1.5)
                .dimensions("10x10x10 cm")
                .color("Default Color")
                .brand("Default Brand")
                .category(category)
                .build();
    }
}
