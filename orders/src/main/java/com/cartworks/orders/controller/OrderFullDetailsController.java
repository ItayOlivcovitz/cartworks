package com.cartworks.orders.controller;


import com.cartworks.orders.dto.OrderFullDetailsDto;
import com.cartworks.orders.dto.OrderItemFullDetailsDto;
import com.cartworks.orders.service.IOrderFullDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(path="/api/OrdersFullDetailsDto/", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated

public class OrderFullDetailsController {
    private final IOrderFullDetails iOrderFullDetails;
    private static final Logger logger = LoggerFactory.getLogger(OrderFullDetailsController.class);



    public OrderFullDetailsController(IOrderFullDetails iOrderFullDetails) {
        this.iOrderFullDetails = iOrderFullDetails;
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<OrderFullDetailsDto>> fetchOrderDetails(
                                                                        @RequestHeader("cartworks-correlation-id") String correlationId,
                                                                        @PathVariable
                                                                        @Pattern(
                                                                            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                                                                            message = "Invalid email format"
                                                                        ) String email) {
        logger.debug("Start - FetchOrdersFullDetails");
        List<OrderFullDetailsDto> orderFullDetailsDto = iOrderFullDetails.getOrdersByEmail(email,correlationId);
        logger.debug("End - FetchOrdersFullDetails");

        return ResponseEntity.status(HttpStatus.SC_OK).body(orderFullDetailsDto);
    }

}
