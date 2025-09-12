package com.backend.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
//	private JWTAuthenticationFilter jwtAuthenticationFilter;
	private JwtAuthEntryPoint authEntryPoint;

    public SecurityConfig(JwtAuthEntryPoint authEntryPoint) {
//		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.authEntryPoint = authEntryPoint;
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Expose AuthenticationManager as a bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
            	.requestMatchers("/api/auth/login-user").permitAll()
            	.requestMatchers("/api/admin", "/api/role").hasAuthority("Admin")	
            	.requestMatchers("/api/task").hasAnyAuthority("Manager", "Support")
                .anyRequest().authenticated() // Allow all requests (adjust later)
            )
            .csrf(csrf -> csrf.disable()) // Disable CSRF for now (useful for APIs)
        	.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        	.exceptionHandling(handling -> handling.authenticationEntryPoint(authEntryPoint));

        return http.build();
    }
    
    @Bean
	public JWTAuthenticationFilter jwtAuthenticationFilter() {
		return new JWTAuthenticationFilter();
	}

}
