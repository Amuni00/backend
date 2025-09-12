package com.backend.backend.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.backend.backend.dto.LoginDTO;
import com.backend.backend.dto.UserDto;
import com.backend.backend.entity.UserEntity;

public interface UserService {
    UserEntity registerUser(UserEntity user); // registration
    List<UserDto> getAllUsers(); // returns DTO now
    UserDto updateUser(Long id, UserEntity user);
    void deleteUser(Long id);
	//UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
