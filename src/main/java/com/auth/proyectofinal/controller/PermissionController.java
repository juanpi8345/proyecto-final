package com.auth.proyectofinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.proyectofinal.model.Permission;
import com.auth.proyectofinal.model.dto.ResponseMessageDTO;
import com.auth.proyectofinal.service.PermissionService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/permission")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;

	@GetMapping
	public ResponseEntity<List<Permission>> getPermissions(){
		return ResponseEntity.ok(permissionService.findAll());
	}
	

	@GetMapping("/{permissionName}")
	public ResponseEntity<Permission> getPermission(@PathVariable String permissionName){
		return ResponseEntity.ok(permissionService.getPermission(permissionName));
	}

	@PostMapping
	public ResponseEntity<ResponseMessageDTO> createPermission(@RequestBody Permission permission){
		permissionService.savePermission(permission);
		return ResponseEntity.ok(new ResponseMessageDTO("Permission created successfuly",HttpStatus.OK.value()));
	}

	@PutMapping("/{permissionName}")
	public ResponseEntity<ResponseMessageDTO> editPermission(@PathVariable String permissionName, @RequestBody Permission permission){
		permissionService.editPermission(permissionName, permission);
		return ResponseEntity.ok(new ResponseMessageDTO("Permission edited successfuly",HttpStatus.OK.value()));
	}

	@DeleteMapping("/{permissionName}")
	public ResponseEntity<ResponseMessageDTO> deletePermission(@PathVariable String permissionName){
		permissionService.deletePermission(permissionName);
		return ResponseEntity.ok(new ResponseMessageDTO("Permission deleted successfuly",HttpStatus.OK.value()));
	}

}
