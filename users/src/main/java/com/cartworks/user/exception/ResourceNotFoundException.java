package com.cartworks.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%application_qa.yml not found with the given input data %application_qa.yml : '%application_qa.yml'", resourceName, fieldName, fieldValue));
    }

}