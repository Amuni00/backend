package com.backend.backend.service;

import com.backend.backend.entity.Role;
import java.util.List;

public interface RoleService {
    Role createRole(Role role);
    List<Role> getAllRoles();
}
