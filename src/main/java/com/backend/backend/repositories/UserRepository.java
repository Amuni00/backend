package com.backend.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	
}
