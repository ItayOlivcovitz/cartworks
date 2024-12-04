package com.cartworks.orders.mapper;


import com.cartworks.orders.dto.OrderDto;
import com.cartworks.orders.dto.OrderItemDto;
import com.cartworks.orders.entity.Order;
import com.cartworks.orders.entity.OrderItem;
import com.cartworks.orders.enums.OrderStatus;

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
        orderItemDto.setProductName(orderItem.getProductName());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        return orderItemDto;
    }

    public static OrderItem mapToOrderItem(OrderItemDto orderItemDto, Order order) {
        return OrderItem.builder()
                .order(order)
                .productName(orderItemDto.getProductName())
                .quantity(orderItemDto.getQuantity())
                .price(orderItemDto.getPrice())
                .build();
    }


}