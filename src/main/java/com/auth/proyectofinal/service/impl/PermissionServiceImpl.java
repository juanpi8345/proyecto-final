package com.auth.proyectofinal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.proyectofinal.exception.ResourceNotFoundException;
import com.auth.proyectofinal.model.Permission;
import com.auth.proyectofinal.repository.PermissionRepository;
import com.auth.proyectofinal.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	@Override
	public Permission getPermission(String permissionName) {
		return permissionRepository.findById(permissionName)
				.orElseThrow(() -> new ResourceNotFoundException("Permission with name: "+permissionName+ " not found"));
	}

	@Override
	public void savePermission(Permission permission) {
		permissionRepository.save(permission);
	}

	@Override
	public void editPermission(String permissionNameToEdit,Permission newPermission) {
		Permission permission = permissionRepository.findById(permissionNameToEdit)
				.orElseThrow(() -> new ResourceNotFoundException("Permission with name: "+permissionNameToEdit+ " not found"));
		permission.setPermission(newPermission.getPermission());
		this.savePermission(permission);
	}

	@Override
	public void deletePermission(String permissionName) {
		permissionRepository.findById(permissionName)
				.orElseThrow(() -> new ResourceNotFoundException("Permission with name: "+permissionName+ " not found"));
		permissionRepository.deleteById(permissionName);
	}

}
