package com.auth.proyectofinal.service;

import java.util.List;

import com.auth.proyectofinal.model.Role;

public interface RoleService {
	List<Role> findAll();
	Role getRole(String roleName);
	void createRole(Role role);
	void editRole(String roleName, Role role);
	void deleteRole(String roleName);
}
