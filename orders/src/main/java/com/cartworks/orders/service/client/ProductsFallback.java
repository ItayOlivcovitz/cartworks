package com.cartworks.orders.service.client;

import com.cartworks.orders.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

public
@Component class ProductsFallback implements ProductsFeignClient {
    /**
     * @param correlationId
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ProductDto> getProductById(String correlationId, Long id) {
        return null;
    }
}
