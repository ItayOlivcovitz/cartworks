package com.cartworks.products.controller;

import com.cartworks.products.dto.ErrorResponseDto;
import com.cartworks.products.dto.ProductsContactInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class InfoController {

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private ProductsContactInfoDto productsContactInfoDto;

    @Operation(
            summary = "Get Build Info REST API",
            description = "Fetch the current build version of the application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Build info fetched successfully",
                    content = @Content(
                            schema = @Schema(type = "string", example = "1.0.0")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @Operation(
            summary = "Get Java Version REST API",
            description = "Fetch the Java version installed in the environment (JAVA_HOME variable)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Java version fetched successfully",
                    content = @Content(
                            schema = @Schema(type = "string", example = "/usr/lib/jvm/java-11-openjdk-amd64")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get Contact Info REST API",
            description = "Fetch contact information for product support"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contact info fetched successfully",
                    content = @Content(
                            schema = @Schema(implementation = ProductsContactInfoDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<ProductsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productsContactInfoDto);
    }
}
