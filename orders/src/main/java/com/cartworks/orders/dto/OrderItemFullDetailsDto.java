package com.cartworks.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(
        name = "OrderItemDto",
        description = "Schema to hold OrderItem information"
)
public class OrderItemFullDetailsDto {

    @NotBlank(message = "Product  is required")
    private ProductDto product;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Price is required")
    private Double price;
}