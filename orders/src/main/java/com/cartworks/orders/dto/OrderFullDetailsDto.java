package com.cartworks.orders.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;


@Data
public class OrderFullDetailsDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "User email is required")
    private String userEmail;

    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    @NotNull(message = "Total amount is required")
    private Double totalAmount;

    @NotBlank(message = "Order status is required")
    private String status;

    @NotNull(message = "Order items are required")
    private Set<OrderItemFullDetailsDto> items;
}