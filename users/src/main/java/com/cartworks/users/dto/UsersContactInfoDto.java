package com.cartworks.users.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "users")
public record UsersContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
}