package com.backend.backend.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.backend.service.CustomUserDetailsService;
import com.backend.backend.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtGenerator tokenGenerator;
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomUserDetailsService customeCustomUserDetailsService;

	private static final String BEARER_PREFIX = "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = extractToken(request);
		if (token != null && tokenGenerator.validateToken(token)) {
			String username = tokenGenerator.getUsernameFromJwt(token);
			UserDetails userDetails = customeCustomUserDetailsService.loadUserByUsername(username);
			List<SimpleGrantedAuthority> authorities = tokenGenerator.getRolesFromJwt(token).stream()
					.map(role -> new SimpleGrantedAuthority(role)) // Add "ROLE_" prefix [ROLE_Admin, ROlE_Manager]
					.collect(Collectors.toList());
			System.out.println("\n\n" + authorities + "\n\n");
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, authorities);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String extractToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith(BEARER_PREFIX)) {
			return header.substring(BEARER_PREFIX.length());
		}
		return null;
	}
}
