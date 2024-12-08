package com.cartworks.products.controller;

import com.cartworks.products.dto.ProductDto;
import com.cartworks.products.dto.ResponseDto;
import com.cartworks.products.dto.ErrorResponseDto;
import com.cartworks.products.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@Tag(
        name = "CRUD REST APIs for Products in Cartworks",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE product details"
)
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Create Product REST API",
            description = "REST API to create a new product in Cartworks"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ResponseDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        productService.createProduct(productDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201", "Product created successfully", productDto));
    }


    @Operation(
            summary = "Fetch Product by ID REST API",
            description = "REST API to fetch product details by ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = ProductDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productDto);
    }

    @Operation(
            summary = "Fetch All Products REST API",
            description = "REST API to fetch all products"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Products retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = List.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @Operation(
            summary = "Update Product REST API",
            description = "REST API to update product details by ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductDto>> updateProduct(@PathVariable Long id,
                                                                 @Valid @RequestBody ProductDto productDto) {
        productService.updateProduct(id, productDto); // Throws ResourceNotFoundException if the product doesn't exist
        ProductDto updatedProduct = productService.getProductById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(
                        "200",
                        "Product updated successfully",
                        updatedProduct
                ));
    }


    @Operation(
            summary = "Delete Product REST API",
            description = "REST API to delete product details by ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product deleted successfully",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id); // Throws ResourceNotFoundException if the product doesn't exist
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(
                        "200",
                        "Product deleted successfully",
                        null
                ));
    }

}
