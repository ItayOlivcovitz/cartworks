package com.cartworks.orders.controller;


import com.cartworks.orders.dto.OrderFullDetailsDto;
import com.cartworks.orders.dto.OrderItemFullDetailsDto;
import com.cartworks.orders.service.IOrderFullDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "REST API for Customers in Cartworks",
        description = "REST APIs in EazyBank to FETCH user orders details"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated

public class OrderFullDetailsController {
    private final IOrderFullDetails iOrderFullDetails;


    public OrderFullDetailsController(IOrderFullDetails iOrderFullDetails) {
        this.iOrderFullDetails = iOrderFullDetails;
    }

    @GetMapping("/OrdersFullDetailsDto/{email}")
    public ResponseEntity<List<OrderFullDetailsDto>> fetchOrderDetails(@PathVariable String email) {
        List<OrderFullDetailsDto>  orderFullDetailsDto = iOrderFullDetails.getOrdersByEmail(email);

        return ResponseEntity.status(HttpStatus.SC_OK).body(orderFullDetailsDto);

    }

}
