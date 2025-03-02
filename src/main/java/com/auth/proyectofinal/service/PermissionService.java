package com.auth.proyectofinal.service;

import java.util.List;

import com.auth.proyectofinal.model.Permission;

public interface PermissionService {
	List<Permission> findAll();
	Permission getPermission(String permissionName);
	void savePermission(Permission permission);
	void editPermission(String permissionToEditName, Permission permission);
	void deletePermission(String permissionName);
}
