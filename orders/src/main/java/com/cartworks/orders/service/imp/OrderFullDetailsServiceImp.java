package com.cartworks.orders.service.imp;

import com.cartworks.orders.dto.*;
import com.cartworks.orders.exception.ResourceNotFoundException;
import com.cartworks.orders.mapper.OrderMapper;
import com.cartworks.orders.service.IOrderService;
import com.cartworks.orders.service.IOrderFullDetails;
import com.cartworks.orders.service.client.ProductsFeignClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@Data
@AllArgsConstructor
public class OrderFullDetailsServiceImp implements IOrderFullDetails {
    private IOrderService orderService;
    private ProductsFeignClient productsFeignClient;

    /**
     * @param email
     * @return
     */
    @Override
    public List<OrderFullDetailsDto> getOrdersByEmail(String email,String correlationId) {
        // Fetch orders by email
        List<OrderDto> ordersList = orderService.getOrdersByUserEmail(email);

        if (ordersList.isEmpty()) {
            throw new ResourceNotFoundException("Order", "email", email);
        }

        // Map each OrderDto to OrderFullDetailsDto with enriched product details
        List<OrderFullDetailsDto> orderFullDetailsList = new ArrayList<>();
        for (OrderDto orderDto : ordersList) {
            // Convert OrderDto to OrderFullDetailsDto
            OrderFullDetailsDto orderFullDetailsDto = OrderMapper.mapToOrderFullDetailsDto(orderDto);
            System.out.println("Mapped OrderDto to OrderFullDetailsDto: " + orderFullDetailsDto);

            // Enrich order items
            Set<OrderItemFullDetailsDto> enrichedItems = new HashSet<>();
            for (OrderItemDto orderItemDto : orderDto.getItems()) {
                try {
                    // Fetch product details
                    ProductDto productDto = fetchProductById(orderItemDto.getProductId(),correlationId);
                    System.out.println("Fetched ProductDto: " + productDto);

                    // Populate OrderItemFullDetailsDto
                    OrderItemFullDetailsDto orderItemFullDetailsDto = new OrderItemFullDetailsDto();
                    orderItemFullDetailsDto.setProduct(productDto);
                    orderItemFullDetailsDto.setQuantity(orderItemDto.getQuantity());
                    orderItemFullDetailsDto.setPrice(orderItemDto.getPrice());
                    System.out.println("Created OrderItemFullDetailsDto: " + orderItemFullDetailsDto);

                    enrichedItems.add(orderItemFullDetailsDto);
                } catch (ResourceNotFoundException e) {
                    System.err.println("Failed to fetch product for ID: " + orderItemDto.getProductId() + ". Reason: " + e.getMessage());
                    // Optionally, you can skip the item or handle it differently here
                }
            }

            // Add enriched items to the order
            orderFullDetailsDto.setItems(enrichedItems);
            System.out.println("Set enriched items in OrderFullDetailsDto: " + orderFullDetailsDto);

            orderFullDetailsList.add(orderFullDetailsDto);
        }

        System.out.println("Final enriched orders list for email " + email + ": " + orderFullDetailsList);
        return orderFullDetailsList;
    }

    /**
     * Fetches ProductDto by product ID using ProductsFeignClient
     *
     * @param productId the product ID
     * @return the ProductDto
     */
    private ProductDto fetchProductById(Long productId,String correlationId) {
        try {
            ResponseEntity<ProductDto> productResponse = productsFeignClient.getProductById( correlationId,productId);
            if (productResponse != null && productResponse.getBody() != null) {
                return productResponse.getBody();
            } else {
                System.err.println("Product not found for ID: " + productId);
                throw new ResourceNotFoundException("Product", "id", productId.toString());
            }
        } catch (Exception e) {
            System.err.println("Error while fetching product for ID: " + productId + ". Reason: " + e.getMessage());
            throw new ResourceNotFoundException("Product", "id", productId.toString());
        }
    }

}
