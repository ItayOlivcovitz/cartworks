package com.cartworks.orders;

import com.cartworks.orders.dto.OrderContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Entry point for the Orders microservice in the CartWorks application.
 * This microservice manages order-related operations, including creating, updating, and retrieving orders.
 *
 * Key Features:
 * - Integration with JPA for database operations.
 * - Swagger/OpenAPI support for API documentation.
 * - Supports auditing for tracking entity changes.
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {OrderContactInfoDto.class})
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "CartWorks Orders Microservice API Documentation",
				description = "REST API documentation for the Orders microservice of the CartWorks platform.",
				version = "v1",
				contact = @Contact(
						name = "CartWorks Support Team",
						email = "support@cartworks.com",
						url = "https://www.cartworks.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Full API Documentation",
				url = "https://www.cartworks.com/swagger-ui.html"
		)
)
public class OrdersApplication {

	/**
	 * Main method to bootstrap the Orders microservice application.
	 *
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(OrdersApplication.class, args);
	}
}
