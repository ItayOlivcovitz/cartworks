package com.cartworks.users.service;

import com.cartworks.users.dto.UserDto;

public interface IUserService {
    void registerUser(UserDto userDto);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    boolean updateUser(String email, UserDto userDto);

    boolean deleteUser(Long id);

    boolean deleteUserByEmail(String email);

    boolean existsByEmail(String email); // Add this method
}
