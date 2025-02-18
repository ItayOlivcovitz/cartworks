package com.cartworks.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(
        name = "ProductDto",
        description = "Schema to hold product information"
)
public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private Integer stock;

    private String imageUrl;

    @NotNull(message = "Weight is required")
    @Min(value = 0, message = "Weight must be greater than or equal to 0")
    private Double weight;

    private String dimensions;

    private Boolean isAvailable;

    @NotBlank(message = "Color is required")
    private String color;

    @NotBlank(message = "Brand is required")
    private String brand;

    private String material;


    private Long categoryId;
}
