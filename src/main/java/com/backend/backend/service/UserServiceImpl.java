package com.backend.backend.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.backend.dto.RoleDto;
import com.backend.backend.dto.UserDto;
import com.backend.backend.entity.Role;
import com.backend.backend.entity.UserEntity;
import com.backend.backend.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register new user
    @Override
    public UserEntity registerUser(UserEntity user) {
     user.setPassword(passwordEncoder.encode(user.getPassword())); // encode password
    	
    	  //user.setPassword(user.getPassword()); // encode password
        return userRepository.save(user);
    }

    // Get all users as DTO
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getRole() != null ? new RoleDto(user.getRole().getName()) : null,
                        null // token is null here
                ))
                .collect(Collectors.toList());
    }

    // Update user
    @Override
    public UserDto updateUser(Long id, UserEntity user) {
        UserEntity existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setPhoneNumber(user.getPhoneNumber());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        existing.setRole(user.getRole());

        UserEntity updated = userRepository.save(existing);

        return new UserDto(
                updated.getId(),
                updated.getFirstName(),
                updated.getLastName(),
                updated.getUsername(),
                updated.getEmail(),
                updated.getPhoneNumber(),
                updated.getRole() != null ? new RoleDto(updated.getRole().getName()) : null,
                null
        );
    }

    // Delete user
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
    
    
   
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity userEntity = userRepository.findByUsername(username)
//            .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
//
//        return new org.springframework.security.core.userdetails.User(
//            userEntity.getUsername(),
//            userEntity.getPassword(),
//            mapRoleToAuthority(userEntity.getRole())
//        );
//    }
//
//    private Collection<GrantedAuthority> mapRoleToAuthority(Role role) {
//        if (role == null) return List.of();
//        return List.of(new SimpleGrantedAuthority("ROLE_"+ role.getName())); // e.g., "ROLE_ADMIN"
//    }


}
