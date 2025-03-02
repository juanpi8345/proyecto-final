package com.auth.proyectofinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.proyectofinal.model.dto.AuthLoginRequestDTO;
import com.auth.proyectofinal.model.dto.AuthLoginResponseDTO;
import com.auth.proyectofinal.model.dto.AuthRegisterRequestDTO;
import com.auth.proyectofinal.model.dto.AuthRegisterResponseDTO;
import com.auth.proyectofinal.service.impl.UserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class AuthController {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@PostMapping("/login")
	public ResponseEntity<AuthLoginResponseDTO> login(@RequestBody AuthLoginRequestDTO request) {
		return ResponseEntity.ok(userDetailsServiceImpl.login(request));
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthRegisterResponseDTO> register(@RequestBody AuthRegisterRequestDTO request) {
		return ResponseEntity.ok(userDetailsServiceImpl.register(request));
	}

}
