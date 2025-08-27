package com.backend.backend.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import com.backend.backend.entity.User;
import com.backend.backend.exception.ValidationException;
import com.backend.backend.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register User with Validations
    public User registerUser(User user) {
        validateUser(user);

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

        return userRepository.save(user);
    }

    // Update User
    public User updateUser(Long id, User user) {
        validateUser(user);

        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setRole(user.getRole());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ValidationException("User not found with id " + id));
    }

    // Delete
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ValidationException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    // Get all
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Validation logic
    private void validateUser(User user) {
        // First & Last Name
        if (user.getFirstName() == null || !user.getFirstName().matches("^[A-Za-z]+$")) {
            throw new ValidationException("First name must only contain letters and cannot be empty.");
        }
        if (user.getLastName() == null || !user.getLastName().matches("^[A-Za-z]+$")) {
            throw new ValidationException("Last name must only contain letters and cannot be empty.");
        }

        // Username
        if (user.getUsername() == null || !user.getUsername().matches("^[a-zA-Z0-9._]{5,}$")) {
            throw new ValidationException("Username must be at least 5 characters and contain only letters, numbers, '.' or '_'.");
        }

        // Email
        if (user.getEmail() == null || !user.getEmail().endsWith("@gmail.com") || user.getEmail().length() > 50) {
            throw new ValidationException("Email must be valid, end with @gmail.com, and not exceed 50 characters. Example: example@gmail.com");
        }

        // Phone Number (Ethiopian format: 09xxxxxxxx)
        if (user.getPhoneNumber() == null || !Pattern.matches("^09[0-9]{8}$", user.getPhoneNumber())) {
            throw new ValidationException("Phone number must start with 09 and be exactly 10 digits long.");
        }

        // Password
        if (user.getPassword() == null || !user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new ValidationException("Password must be at least 8 characters long and include uppercase, lowercase, number, and special character.");
        }

        // Role
        if (user.getRole() == null || !(user.getRole().equals("USER") || user.getRole().equals("ADMIN") || user.getRole().equals("MANAGER"))) {
            throw new ValidationException("Invalid role. Role must be USER, ADMIN, or MANAGER.");
        }
    }
}
