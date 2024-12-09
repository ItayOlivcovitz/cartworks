package com.cartworks.orders.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "orders")
public record OrdersContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
}