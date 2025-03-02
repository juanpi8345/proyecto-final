package com.auth.proyectofinal.model.dto;

public record AuthLoginResponseDTO(String username,
							  String message,
							  String jwt) {}
