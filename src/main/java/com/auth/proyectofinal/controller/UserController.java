package com.auth.proyectofinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.proyectofinal.model.UserSec;
import com.auth.proyectofinal.model.dto.ResponseMessageDTO;
import com.auth.proyectofinal.service.UserSecService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserSecService userService;
	
	@GetMapping
	public ResponseEntity<List<UserSec>> getUsers(){
		return ResponseEntity.ok(userService.findAll());
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserSec> getUser(@PathVariable Long userId){
		return ResponseEntity.ok(userService.getUser(userId)); 
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ResponseMessageDTO> deleteUser(@PathVariable Long userId){
		userService.deleteUser(userId);
		return ResponseEntity.ok(new ResponseMessageDTO("User deleted successfuly",HttpStatus.OK.value()));
	}
	
}
