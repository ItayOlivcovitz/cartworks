package com.cartworks.orders.service;

import com.cartworks.orders.dto.OrderFullDetailsDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IOrderFullDetails {

    /**
     * Retrieves all orders by a given user email.
     *
     * @param email The user email to retrieve orders for
     * @return List of OrderFullDetailsDto containing order details
     */
    public List<OrderFullDetailsDto> getOrdersByEmail(@PathVariable String email,String correlationId);
}

