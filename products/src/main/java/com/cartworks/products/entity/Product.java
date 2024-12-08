package com.cartworks.products.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Stock is required")
    private Integer stock;

    private String imageUrl;

    @NotNull(message = "Weight is required")
    private Double weight;

    private String dimensions;

    @NotBlank(message = "Color is required")
    private String color;

    @NotBlank(message = "Brand is required")
    private String brand;

    private String material;

    @NotNull(message = "Availability status is required")
    private Boolean isAvailable; // Indicates if the product is available for purchase

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    @JsonBackReference // Prevents recursion by ignoring the parent reference in JSON
    private Category category;
}
