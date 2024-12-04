package com.cartworks.users.service.imp;

import com.cartworks.users.dto.UserDto;
import com.cartworks.users.entity.User;
import com.cartworks.users.exception.ResourceNotFoundException;
import com.cartworks.users.exception.UserAlreadyExistsException;
import com.cartworks.users.mapper.UserMapper;
import com.cartworks.users.repository.UserRepository;
import com.cartworks.users.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@AllArgsConstructor
@Validated
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public void registerUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if(optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("User already registered with given email " + userDto.getEmail());
        }
        User user = UserMapper.mapToUser(userDto, new User());
        userRepository.save(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        return UserMapper.mapToUserDto(user, new UserDto());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));


        return UserMapper.mapToUserDto(user, new UserDto());
    }

    @Override
    public boolean updateUser(Long id, UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", id.toString());
        }

        User user = UserMapper.mapToUser(userDto, userOptional.get());
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id.toString());
        }

        userRepository.deleteById(id);
        return true;
    }
}