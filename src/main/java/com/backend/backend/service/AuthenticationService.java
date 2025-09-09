package com.backend.backend.service;

import com.backend.backend.dto.LoginDto;
import com.backend.backend.entity.User;
import com.backend.backend.security.JwtGenerator;
import com.backend.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    public Map<String, Object> authenticateUser(LoginDto loginDto) {

        // 1. Check if username exists
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Username is incorrect"));

        // 2. Check if password matches
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Password is incorrect");
        }

        // 3. Authenticate using Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. Generate JWT token
        String token = jwtGenerator.generateToken(
                user.getUsername(),
                user.getRole() != null ? 
                  user.getRole().getName() != null ? 
                      java.util.List.of(user.getRole().getName()) : java.util.List.of() 
                  : java.util.List.of()
        );

        // 5. Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user); // you can also map this to UserDto if you prefer

        return response;
    }
}
