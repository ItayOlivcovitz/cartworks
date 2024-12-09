package com.cartworks.products;

import com.cartworks.products.dto.ProductsContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") // Enables JPA Auditing with a custom AuditorAware implementation
@EnableConfigurationProperties(value = {ProductsContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Products Microservice API",
				version = "1.0",
				description = "API documentation for the Products microservice in Cartworks"
		)
)
public class ProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
	}
}
