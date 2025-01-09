package com.cartworks.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator cartworksRouteConfig(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("users_route", r -> r
						.path("/cartworks/users/**")
						.filters(f -> f.rewritePath("/cartworks/users/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.circuitBreaker(config -> config.setName("usersCircuitBreaker")
										.setFallbackUri("forward:/contactSupport")))
						.uri("lb://USERS")) // Corrected URI scheme to ensure proper routing

				.route("orders_route", r -> r
						.path("/cartworks/orders/**")
						.filters(f -> f.rewritePath("/cartworks/orders/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))

						.uri("lb://ORDERS")) // Corrected URI scheme to ensure proper routing

				.route("products_route", r -> r
						.path("/cartworks/products/**")
						.filters(f -> f.rewritePath("/cartworks/products/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))

						.uri("lb://PRODUCTS")) // Corrected URI scheme to ensure proper routing

				.build();
	}
}
