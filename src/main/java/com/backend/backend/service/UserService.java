package com.backend.backend.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.Role;
import com.backend.backend.entity.User;
import com.backend.backend.exception.ValidationException;
import com.backend.backend.repositories.RoleRepository;
import com.backend.backend.repositories.UserRepository;
import com.backend.backend.validation.ValidationUser;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder=passwordEncoder;
    }

    // Register User
    public User registerUser(User user) {
        // ✅ Central validation
        ValidationUser.validate(user);

        // Check uniqueness
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ValidationException("The username is already used. Choose another one.");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ValidationException("The email is already used. Use another one.");
        }
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new ValidationException("The phone number is already registered.");
        }

        // Check Role existence
        Role role = roleRepository.findById(user.getRole().getId())
                .orElseThrow(() -> new ValidationException("Role with ID " + user.getRole().getId() + " does not exist."));
        user.setRole(role);

        // Hash password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // Update User
    public User updateUser(Long id, User user) {
        ValidationUser.validate(user);

        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());

            // Only hash password if it's changed
//            if (!user.getPassword().equals(existingUser.getPassword())) {
//                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
//            }

            existingUser.setRole(roleRepository.findById(user.getRole().getId())
                    .orElseThrow(() -> new ValidationException("Role with ID " + user.getRole().getId() + " does not exist.")));

            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ValidationException("User not found with id " + id));
    }

    // Delete User
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ValidationException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
