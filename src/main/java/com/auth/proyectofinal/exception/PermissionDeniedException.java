package com.auth.proyectofinal.exception;

public class PermissionDeniedException extends RuntimeException{

	private static final long serialVersionUID = 5103592045384442063L;

	public PermissionDeniedException(String message) {
		super(message);
	}

}
