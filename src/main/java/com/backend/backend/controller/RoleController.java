package com.backend.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.entity.Role;
import com.backend.backend.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {

	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	// Create
	@PostMapping("/register-role")
	public ResponseEntity<String> createRole(@RequestBody Role role) {
	    Role savedRole = roleService.registerRole(role);
	    return ResponseEntity.ok("Role registered successfully");
	}


	// Read (all)
	@GetMapping("/get-roles")
	public ResponseEntity<List<Role>> getAllRoles() {
		return ResponseEntity.ok(roleService.getAllRoles());
	}
	
}