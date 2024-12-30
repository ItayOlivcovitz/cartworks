package com.cartworks.orders.service.imp;

import com.cartworks.orders.dto.OrderDto;
import com.cartworks.orders.dto.OrderItemDto;
import com.cartworks.orders.entity.Order;
import com.cartworks.orders.entity.OrderItem;
import com.cartworks.orders.enums.OrderStatus;
import com.cartworks.orders.exception.ResourceNotFoundException;
import com.cartworks.orders.mapper.OrderMapper;
import com.cartworks.orders.repository.OrderRepository;
import com.cartworks.orders.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemServiceImpl orderItemService;


    public OrderServiceImpl(OrderRepository orderRepository,OrderItemServiceImpl orderItemService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
    }

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        // Print the incoming order DTO for debugging purposes
        System.out.println("Incoming Order DTO: " + orderDto);

        // Step 1: Map OrderDto to Order entity without setting items yet
        Order order = Order.builder()
                .userEmail(orderDto.getUserEmail())
                .orderDate(orderDto.getOrderDate())
                .totalAmount(orderDto.getTotalAmount())
                .status(OrderStatus.PENDING) // Set initial status to PENDING
                .build();

        // Step 2: Extract the order items from OrderDto and set them to the order
        Set<OrderItem> orderItems = orderDto.getItems().stream()
                .map(itemDto -> OrderItem.builder()
                        .order(order) // Set the order reference to avoid recursion
                        .productName(itemDto.getProductName())
                        .quantity(itemDto.getQuantity())
                        .price(itemDto.getPrice())
                        .build())
                .collect(Collectors.toSet());

        // Set the items to the order
        order.setItems(orderItems);

        // Save the Order entity to the database
        Order savedOrder = orderRepository.save(order);

        // Return the saved Order entity as an OrderDto
        OrderDto savedOrderDto = OrderMapper.mapToOrderDto(savedOrder);

        System.out.println(savedOrderDto.getId());
        return savedOrderDto;
    }




    @Override
    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "ID", String.valueOf(orderId)));
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        // Find the existing order
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "ID", String.valueOf(orderId)));

        // Update order fields
        existingOrder.setTotalAmount(orderDto.getTotalAmount());
        existingOrder.setStatus(OrderStatus.valueOf(orderDto.getStatus()));
        existingOrder.setOrderDate(orderDto.getOrderDate());
        existingOrder.setUserEmail(orderDto.getUserEmail());

        // Update items
        Set<OrderItem> currentItems = existingOrder.getItems();

        // Find and update existing items or add new ones
        for (OrderItemDto itemDto : orderDto.getItems()) {
            OrderItem existingItem = currentItems.stream()
                    .filter(item -> item.getProductName().equals(itemDto.getProductName())) // Assuming productName is unique
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                // Update existing item
                existingItem.setQuantity(itemDto.getQuantity());
                existingItem.setPrice(itemDto.getPrice());
            } else {
                // Create and add new item
                OrderItem newItem = OrderMapper.mapToOrderItem(itemDto, existingOrder);
                currentItems.add(newItem);
            }
        }

        // Remove items not present in the new list
        currentItems.removeIf(existingItem -> orderDto.getItems().stream()
                .noneMatch(dto -> dto.getProductName().equals(existingItem.getProductName())));

        // Save the updated order
        Order updatedOrder = orderRepository.save(existingOrder);
        return OrderMapper.mapToOrderDto(updatedOrder);
    }



    @Override
    @Transactional
    public boolean deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        throw  new  ResourceNotFoundException("Order", "ID", String.valueOf(orderId));
    }

    @Override
    public List<OrderDto> getOrdersByUserEmail(String userEmail) {
        List<Order> orders = orderRepository.findByUserEmail(userEmail);

        if(orders.isEmpty()) {
            throw new ResourceNotFoundException("Order", "email", userEmail);
        }
        return orders.stream()
                .map(OrderMapper::mapToOrderDto)
                .collect(Collectors.toList());
    }
}
