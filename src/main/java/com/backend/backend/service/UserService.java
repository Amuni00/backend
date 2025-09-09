package com.backend.backend.service;

import java.util.List;

import com.backend.backend.dto.LoginDto;
import com.backend.backend.dto.UserDto;
import com.backend.backend.entity.User;

public interface UserService {
    User registerUser(User user); // registration
    UserDto login(LoginDto loginDto); // login with JWT
    List<UserDto> getAllUsers(); // returns DTO now
    UserDto updateUser(Long id, User user);
    void deleteUser(Long id);
}
