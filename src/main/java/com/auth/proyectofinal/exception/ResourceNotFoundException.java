package com.auth.proyectofinal.exception;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 5103592045384442064L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
