package com.auth.proyectofinal.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.proyectofinal.exception.ResourceNotFoundException;
import com.auth.proyectofinal.model.Permission;
import com.auth.proyectofinal.model.Role;
import com.auth.proyectofinal.repository.PermissionRepository;
import com.auth.proyectofinal.repository.RoleRepository;
import com.auth.proyectofinal.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role getRole(String roleName) {
		return roleRepository.findById(roleName)
				.orElseThrow(()->new ResourceNotFoundException("Role with name: "+roleName+" not found"));
	}

	@Override
	public void createRole(Role role) {
		Set<Permission> permissionSet = new HashSet<Permission>();
		for(Permission permission : role.getPermissionSet()) {
			Optional<Permission> readPermission = permissionRepository.findById(permission.getPermission());
			if(readPermission.isPresent())
				permissionSet.add(permission);
			else
				throw new ResourceNotFoundException("List of permissions not found");
		}
			
		role.setPermissionSet(permissionSet);
		roleRepository.save(role);
	}

	@Override
	public void editRole(String roleName, Role role) {
		role.setRoleName(roleName);
		this.createRole(role);
	}
	
	@Override
	public void deleteRole(String roleName){
		roleRepository.findById(roleName)
				.orElseThrow(()->new ResourceNotFoundException("Role with name: "+roleName+" not found"));
		roleRepository.deleteById(roleName);
	}
	

}
