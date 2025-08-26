package com.backend.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	
}
