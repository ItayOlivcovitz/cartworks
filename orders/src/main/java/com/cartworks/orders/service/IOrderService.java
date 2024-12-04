package com.cartworks.orders.service;


import com.cartworks.orders.dto.OrderDto;

public interface IOrderService {
    /**
     * Create a new order.
     *
     * @param orderDto Order details
     * @return Created OrderDto
     */
    OrderDto createOrder(OrderDto orderDto);

    /**
     * Retrieve an order by its ID.
     *
     * @param orderId ID of the order
     * @return OrderDto containing order details
     */
    OrderDto getOrder(Long orderId);

    /**
     * Update an existing order.
     *
     * @param orderId ID of the order to be updated
     * @param orderDto Updated order details
     * @return Updated OrderDto
     */
    OrderDto updateOrder(Long orderId, OrderDto orderDto);

    /**
     * Delete an order by its ID.
     *
     * @param orderId ID of the order to be deleted
     * @return true if the order was successfully deleted, false otherwise
     */
    boolean deleteOrder(Long orderId);
}
