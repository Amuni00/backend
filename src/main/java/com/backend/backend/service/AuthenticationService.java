package com.backend.backend.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.backend.config.JwtGenerator;
import com.backend.backend.dto.LoginDTO;
import com.backend.backend.entity.UserEntity;
import com.backend.backend.repositories.UserRepository;


@Service
public class AuthenticationService {
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtGenerator jwtGenerator;

	@Autowired
	public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository,
			PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtGenerator = jwtGenerator;
	}
	public Map<String, Object> authenticateUser(LoginDTO userDto) {
	    // Let Spring Security do the authentication (no manual password check)
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(
	            userDto.getUsername(),
	            userDto.getPassword()
	        )
	    );

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    // Fetch the user only for response (not for password validation)
	    UserEntity user = userRepository.findByUsername(userDto.getUsername())
	            .orElseThrow(() -> new BadCredentialsException("Username is incorrect: " + userDto.getUsername()));

	    String token = jwtGenerator.tokenGenerator(authentication);

	    Map<String, Object> response = new HashMap<>();
	    response.put("token", token);
	    response.put("user", user);
	    return response;
	}


}
