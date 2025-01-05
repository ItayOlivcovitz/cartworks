package com.cartworks.orders.service.client;

import com.cartworks.orders.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("products")
public interface ProductsFeignClient {
    @GetMapping(value = "/api/products/{id}", consumes = "application/json")
    public ResponseEntity<ProductDto> getProductById(@RequestHeader("cartworks-correlation-id") String correlationId,
                                                     @PathVariable Long id);
}
