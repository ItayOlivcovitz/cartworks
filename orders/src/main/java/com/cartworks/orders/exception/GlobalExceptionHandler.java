package com.cartworks.orders.exception;

import com.cartworks.orders.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,
                                                                  WebRequest webRequest) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                            WebRequest webRequest) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> validationErrors = new HashMap<>();

        // Extract validation errors
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString(); // e.g., "getOrdersByUserEmail.userEmail"
            String errorMessage = violation.getMessage(); // e.g., "Invalid email format"

            // Extract only the parameter name (e.g., "userEmail") from the field path
            String parameterName = fieldName.substring(fieldName.indexOf('.') + 1);
            validationErrors.put(parameterName, errorMessage);
        }

        // Build response
        response.put("apiPath", ex.getMessage()); // You can customize this
        response.put("errorCode", "BAD_REQUEST");
        response.put("errorMessage", validationErrors);
        response.put("errorTime", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}