package com.backend.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.User;
import com.backend.backend.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User registerUser(User user) {
		return userRepository.save(user);
	}

	public User updateUser(Long id, User user) {
		return userRepository.findById(id).map(existingUser -> {
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setPhoneNumber(user.getPhoneNumber());
			existingUser.setUsername(user.getUsername());
			existingUser.setEmail(user.getEmail());
			existingUser.setPassword(user.getPassword());
			existingUser.setRole(user.getRole());
			return userRepository.save(existingUser);
		}).orElseThrow(() -> new RuntimeException("User not found with id " + id));
	}

	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new RuntimeException("User not found with id " + id);
		}
		userRepository.deleteById(id);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
