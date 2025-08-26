package com.backend.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.Role;
import com.backend.backend.repositories.RoleRepository;

@Service
public class RoleService {

	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public Role registerRole(Role role) {
		return roleRepository.save(role);
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

}
