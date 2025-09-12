package com.backend.backend.validation;

import com.backend.backend.entity.UserEntity;
import com.backend.backend.exception.ValidationException;

public class ValidationUser {

    public static void validate(UserEntity user) {
        // Username not empty
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new ValidationException("Username cannot be empty");
        }

        // Phone number 10 digits
        if (user.getPhoneNumber() == null || !user.getPhoneNumber().matches("\\d{10}")) {
            throw new ValidationException("Phone number must be 10 digits (example: 0912345678)");
        }

        // Email contains @gmail.com
        if (user.getEmail() == null || !user.getEmail().endsWith("@gmail.com")) {
            throw new ValidationException("Email must end with @gmail.com (example: user@gmail.com)");
        }

        // Password not empty
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new ValidationException("Password cannot be empty");
        }
    }
}
