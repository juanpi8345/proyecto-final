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

import com.auth.proyectofinal.model.Role;
import com.auth.proyectofinal.model.dto.ResponseMessageDTO;
import com.auth.proyectofinal.service.RoleService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@GetMapping
	public ResponseEntity<List<Role>> getRoles(){
		return ResponseEntity.ok(roleService.findAll());
	}
	
	@GetMapping("/{roleName}")
	public ResponseEntity<Role> getRole(@PathVariable String roleName){
		return ResponseEntity.ok(roleService.getRole(roleName));
	}

	@PostMapping
	public ResponseEntity<ResponseMessageDTO> createRole(@RequestBody Role role){
		roleService.createRole(role);
		return ResponseEntity.ok(new ResponseMessageDTO("Role created successfuly",HttpStatus.OK.value()));
	}
	
	@PutMapping("/{roleName}")
	public ResponseEntity<ResponseMessageDTO> editRole(@PathVariable String roleName, @RequestBody Role role){
		roleService.editRole(roleName, role);
		return ResponseEntity.ok(new ResponseMessageDTO("Role updated successfuly",HttpStatus.OK.value()));
	}
	
	@DeleteMapping("/{roleName}")
	public ResponseEntity<ResponseMessageDTO> deleteRole(@PathVariable String roleName){
		roleService.deleteRole(roleName);
		return ResponseEntity.ok(new ResponseMessageDTO("Role deleted successfuly",HttpStatus.OK.value()));
	}
}
