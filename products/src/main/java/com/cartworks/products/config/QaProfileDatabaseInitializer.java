package com.cartworks.products.config;

import com.cartworks.products.entity.Category;
import com.cartworks.products.entity.Product;
import com.cartworks.products.repository.CategoryRepository;
import com.cartworks.products.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Profile("qa")
public class QaProfileDatabaseInitializer {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    // Lists for dynamic assignment of color and brand
    private final List<String> colors = List.of("Red", "Blue", "Green", "Yellow", "Black", "White");
    private final List<String> brands = List.of("Brand A", "Brand B", "Brand C", "Brand D", "Brand E");
    private final AtomicInteger colorIndex = new AtomicInteger(0);
    private final AtomicInteger brandIndex = new AtomicInteger(0);

    public QaProfileDatabaseInitializer(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void init() {
        // Create categories if they don't already exist
        List<Category> categories = List.of(
                Category.builder().name("Electronics").build(),
                Category.builder().name("Furniture").build(),
                Category.builder().name("Clothing").build(),
                Category.builder().name("Books").build()
        );

        // Filter out existing categories by name
        List<String> existingCategoryNames = categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .toList();

        List<Category> newCategories = categories.stream()
                .filter(category -> !existingCategoryNames.contains(category.getName()))
                .toList();

        List<Category> persistedCategories = categoryRepository.saveAll(newCategories);
        categoryRepository.flush(); // Ensure IDs are generated

        // Create products only if they don't already exist
        persistedCategories.forEach(category -> {
            List<Product> products = List.of(
                    createProduct("Product 1 for " + category.getName(), category),
                    createProduct("Product 2 for " + category.getName(), category),
                    createProduct("Product 3 for " + category.getName(), category)
            );

            // Filter out existing products by name
            List<String> existingProductNames = productRepository.findAll()
                    .stream()
                    .map(Product::getName)
                    .toList();

            List<Product> newProducts = products.stream()
                    .filter(product -> !existingProductNames.contains(product.getName()))
                    .toList();

            productRepository.saveAll(newProducts);
        });
    }

    private Product createProduct(String name, Category category) {
        // Assign unique color and brand
        String color = colors.get(colorIndex.getAndIncrement() % colors.size());
        String brand = brands.get(brandIndex.getAndIncrement() % brands.size());

        return Product.builder()
                .name(name)
                .description("Description of " + name)
                .price(100.0)
                .stock(10)
                .isAvailable(true)
                .weight(1.5)
                .dimensions("10x10x10 cm")
                .color(color)
                .brand(brand)
                .category(category)
                .build();
    }
}
