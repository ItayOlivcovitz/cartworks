package com.cartworks.user.service;

import com.cartworks.user.dto.UserDto;
import com.cartworks.user.repository.UserRepository;
import com.cartworks.user.entity.User;
import com.cartworks.user.exception.ResourceNotFoundException;
import com.cartworks.user.exception.UserAlreadyExistsException;
import com.cartworks.user.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Validated
@Tag(
        name = "User Service",
        description = "CRUD REST APIs for User management in the CartWorks system"
)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "Register User", description = "Register a new user in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "409", description = "Email or phone number already in use",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> registerUser(@Validated @RequestBody UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use.");
        }
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new UserAlreadyExistsException("Phone number is already in use.");
        }
        User user = UserMapper.mapToUser(userDto, new User());
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    @Operation(summary = "Get User By ID", description = "Fetch a user'application_qa.yml details by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<Optional<UserDto>> getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", id.toString());
        }
        UserDto userDto = UserMapper.mapToUserDto(userOptional.get(), new UserDto());
        return ResponseEntity.ok(Optional.of(userDto));
    }

    @Operation(summary = "Update User", description = "Update an existing user'application_qa.yml information by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> updateUser(Long id, UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", id.toString());
        }
        User user = UserMapper.mapToUser(userDto, userOptional.get());
        userRepository.save(user);
        return ResponseEntity.ok("User updated successfully.");
    }

    @Operation(summary = "Delete User", description = "Delete a user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id.toString());
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
