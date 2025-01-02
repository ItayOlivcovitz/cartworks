package com.cartworks.orders.service;

import com.cartworks.orders.dto.OrderFullDetailsDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IOrderFullDetails {

    public List<OrderFullDetailsDto> getOrdersByEmail(@PathVariable String email);
}

