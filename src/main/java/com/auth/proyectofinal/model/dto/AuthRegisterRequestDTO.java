package com.auth.proyectofinal.model.dto;

public record AuthRegisterRequestDTO(String username,
									 String password,
									 String role) {}
