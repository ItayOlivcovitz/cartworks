package com.cartworks.users.service.client;

import com.cartworks.users.dto.OrdersDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("ORDERS")
public interface OrdersFeignClient {
    @GetMapping(value = "/api/orders/user/fetchByEmail/{userEmail}", consumes = "application/json")
    ResponseEntity<List<OrdersDto>> fetchOrdersByUserEmail(@PathVariable("userEmail") String userEmail);

}
