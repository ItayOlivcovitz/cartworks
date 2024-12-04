package com.cartworks.orders.service.imp;

import com.cartworks.orders.exception.ResourceNotFoundException;
import com.cartworks.orders.mapper.OrderMapper;
import com.cartworks.orders.dto.OrderItemDto;
import com.cartworks.orders.entity.Order;
import com.cartworks.orders.entity.OrderItem;
import com.cartworks.orders.repository.OrderItemRepository;
import com.cartworks.orders.repository.OrderRepository;
import com.cartworks.orders.service.IOrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements IOrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Set<OrderItemDto> addItemsToOrder(Long orderId, Set<OrderItemDto> orderItemDtos) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            Set<OrderItem> orderItems = orderItemDtos.stream()
                    .map(itemDto -> OrderMapper.mapToOrderItem(itemDto, order))
                    .collect(Collectors.toSet());
            Set<OrderItem> savedItems = orderItems.stream()
                    .map(orderItemRepository::save)
                    .collect(Collectors.toSet());
            return savedItems.stream()
                    .map(OrderMapper::mapToOrderItemDto)
                    .collect(Collectors.toSet());
        }
        throw  new  ResourceNotFoundException("Order", "ID", String.valueOf(orderId));
    }

    @Override
    public Set<OrderItemDto> getItemsByOrderId(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Set<OrderItem> orderItems = orderOptional.get().getItems();
            return orderItems.stream()
                    .map(OrderMapper::mapToOrderItemDto)
                    .collect(Collectors.toSet());
        }
        throw  new  ResourceNotFoundException("Order", "ID", String.valueOf(orderId));
    }

    @Override
    @Transactional
    public boolean deleteItem(Long itemId) {
        if (orderItemRepository.existsById(itemId)) {
            orderItemRepository.deleteById(itemId);
            return true;
        }
        throw  new  ResourceNotFoundException("Item", "ID", String.valueOf(itemId));
    }

    @Transactional
    @Override
    public void deleteItemsByOrderId(Long orderId) {
        // Find the order, or throw an exception if not found
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "ID", String.valueOf(orderId)));

        // Loop through each order item and delete them using the repository
        order.getItems().forEach(orderItem -> orderItemRepository.deleteById(orderItem.getId()));

        // Clear the items from the order to remove the association
        order.getItems().clear();
    }

}
