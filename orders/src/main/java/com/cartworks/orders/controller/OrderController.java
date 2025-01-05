package com.cartworks.orders.controller;

import com.cartworks.orders.dto.OrderDto;
import com.cartworks.orders.dto.OrderContactInfoDto;
import com.cartworks.orders.dto.ResponseDto;
import com.cartworks.orders.dto.ErrorResponseDto;
import com.cartworks.orders.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("/api/")
@Tag(
        name = "CRUD REST APIs for Orders in Cartworks",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH, and DELETE order details"
)
public class OrderController {

    @Autowired
    private  IOrderService orderService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private  Environment environment;

    @Autowired
    private OrderContactInfoDto ordersContactInfoDto;

    @Operation(
            summary = "Create Order REST API",
            description = "REST API to create a new order in Cartworks"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input provided",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ResponseDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201", "Order created successfully with ID: " + createdOrder.getId()));
    }

    @Operation(
            summary = "Fetch Order Details REST API",
            description = "REST API to fetch order details by ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = OrderDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable @Valid @Positive(message = "Order ID must be a positive number") Long orderId) {
        OrderDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @Operation(
            summary = "Update Order REST API",
            description = "REST API to update order details by ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/{orderId}")
    public ResponseEntity<ResponseDto> updateOrder(@PathVariable Long orderId, @Valid @RequestBody OrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrder(orderId, orderDto);
        if (updatedOrder != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto("200", "Order updated successfully"));
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto("404", "Order not found"));
    }

@Operation(
        summary = "Get Orders by User Email REST API",
        description = "REST API to retrieve a list of orders by user email"
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK",
                content = @Content(
                        schema = @Schema(implementation = OrderDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "No orders found for the given email",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                )
        )
})
@GetMapping("user/fetchByEmail/{userEmail}")
public ResponseEntity<List<OrderDto>> getOrdersByUserEmail(
        @PathVariable @Valid
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "Invalid email format"
        ) String userEmail) {
    List<OrderDto> orders = orderService.getOrdersByUserEmail(userEmail);
    if (orders.isEmpty()) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Collections.emptyList());
    }
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(orders);
}

    @Operation(
            summary = "Delete Order REST API",
            description = "REST API to delete order details by ID"
    )
    @ApiResponses({
            @ApiResponse(

                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ResponseDto> deleteOrder(@PathVariable Long orderId) {
        boolean isDeleted = orderService.deleteOrder(orderId);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto("200", "Order deleted successfully"));
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto("404", "Order not found"));
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @Operation(
            summary = "Get Java version",
            description = "Get Java versions details that is installed into cards microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<OrderContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ordersContactInfoDto);
    }
}
