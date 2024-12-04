package com.cartworks.orders.service;

import com.cartworks.orders.dto.OrderItemDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface IOrderItemService {
    /**
     * Add items to an order.
     *
     * @param orderId ID of the order to add items to
     * @param orderItemDtos Set of order item details to be added
     * @return Set of added OrderItemDto
     */
    Set<OrderItemDto> addItemsToOrder(Long orderId, Set<OrderItemDto> orderItemDtos);

    /**
     * Retrieve items by order ID.
     *
     * @param orderId ID of the order
     * @return Set of OrderItemDto containing item details
     */
    Set<OrderItemDto> getItemsByOrderId(Long orderId);

    /**
     * Delete an order item by its ID.
     *
     * @param itemId ID of the item to be deleted
     * @return true if the item was successfully deleted, false otherwise
     */
    boolean deleteItem(Long itemId);

    @Transactional
    void deleteItemsByOrderId(Long orderId);
}
