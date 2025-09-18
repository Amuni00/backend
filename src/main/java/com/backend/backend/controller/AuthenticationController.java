package com.backend.backend.controller;

import com.backend.backend.dto.LoginDTO;
import com.backend.backend.exception.CustomResponse;
import com.backend.backend.exception.ValidationException;
import com.backend.backend.service.AuthenticationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login-user")
	public ResponseEntity<?> login(@RequestBody LoginDTO userDto) {
		try {
			Map<String, Object> authData = authenticationService.authenticateUser(userDto);
			CustomResponse<Map<String, Object>> response = new CustomResponse<>("Login successful", authData);
			return ResponseEntity.ok(response);
		} catch (BadCredentialsException e) {
			CustomResponse<String> response = new CustomResponse<>("Invalid username or password", null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		} catch (Exception e) {
			System.err.println("Error during authentication: " + e.getMessage());
			CustomResponse<String> response = new CustomResponse<>("An error occurred during login", null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
