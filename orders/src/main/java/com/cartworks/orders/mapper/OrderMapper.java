package com.cartworks.orders.mapper;


import com.cartworks.orders.dto.*;
import com.cartworks.orders.entity.Order;
import com.cartworks.orders.entity.OrderItem;
import com.cartworks.orders.enums.OrderStatus;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDto mapToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserEmail(order.getUserEmail());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setStatus(order.getStatus().name());
        orderDto.setItems(order.getItems().stream().map(OrderMapper::mapToOrderItemDto).collect(Collectors.toSet()));
        return orderDto;
    }

    public static Order mapToOrder(OrderDto orderDto) {
        Order order = Order.builder()
                .userEmail(orderDto.getUserEmail())
                .orderDate(orderDto.getOrderDate())
                .totalAmount(orderDto.getTotalAmount())
                .status(OrderStatus.valueOf(orderDto.getStatus()))
                .build();
        Set<OrderItem> items = orderDto.getItems().stream().map(itemDto -> mapToOrderItem(itemDto, order)).collect(Collectors.toSet());
        order.setItems(items);
        return order;
    }

    public static OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(orderItem.getProductId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        return orderItemDto;
    }

    public static OrderItem mapToOrderItem(OrderItemDto orderItemDto, Order order) {
        return OrderItem.builder()
                .order(order)
//                .productName(orderItemDto.getProductName())
                .quantity(orderItemDto.getQuantity())
                .price(orderItemDto.getPrice())
                .build();
    }

    // Maps OrderDto to OrderFullDetailsDto
    public static OrderFullDetailsDto mapToOrderFullDetailsDto(OrderDto orderDto) {
        OrderFullDetailsDto orderFullDetailsDto = new OrderFullDetailsDto();
        orderFullDetailsDto.setId(orderDto.getId());
        orderFullDetailsDto.setUserEmail(orderDto.getUserEmail());
        orderFullDetailsDto.setOrderDate(orderDto.getOrderDate());
        orderFullDetailsDto.setTotalAmount(orderDto.getTotalAmount());
        orderFullDetailsDto.setStatus(orderDto.getStatus());
        if (orderDto.getItems() != null) {
            orderFullDetailsDto.setItems(orderDto.getItems().stream()
                    .map(OrderMapper::mapToOrderItemFullDto)
                    .collect(Collectors.toSet()));
        } else {
            orderFullDetailsDto.setItems(Collections.emptySet());
        }
        return orderFullDetailsDto;
    }

    // Maps OrderItemDto to OrderItemFullDetailsDto
    public static OrderItemFullDetailsDto mapToOrderItemFullDto(OrderItemDto orderItemDto) {
        OrderItemFullDetailsDto orderItemFullDetailsDto = new OrderItemFullDetailsDto();
        // Uncomment if product name is relevant
        // orderItemFullDetailsDto.setProductName(orderItemDto.getProductName());
        orderItemFullDetailsDto.setQuantity(orderItemDto.getQuantity());
        orderItemFullDetailsDto.setPrice(orderItemDto.getPrice());
        return orderItemFullDetailsDto;
    }





}