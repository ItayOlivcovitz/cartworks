package com.cartworks.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
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
								.retry(retryConfig -> retryConfig.setRetries(3)
										.setMethods(HttpMethod.GET)
										.setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)))


						.uri("lb://USERS")) // Corrected URI scheme to ensure proper routing

				.route("orders_route", r -> r
						.path("/cartworks/orders/**")
						.filters(f -> f.rewritePath("/cartworks/orders/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.circuitBreaker(config -> config.setName("ordersCircuitBreaker")
										.setFallbackUri("forward:/contactSupport")))
						.uri("lb://ORDERS")) // Corrected URI scheme to ensure proper routing


				.route("products_route", r -> r
						.path("/cartworks/products/**")
						.filters( f -> f.rewritePath("/cartworks/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
										.setKeyResolver(userKeyResolver())))

						.uri("lb://PRODUCTS")) // Corrected URI scheme to ensure proper routing

				.build();
	}
	@Bean
	public RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1, 1, 1);
	}

	@Bean
	KeyResolver userKeyResolver() {
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
				.defaultIfEmpty("anonymous");
	}
}
