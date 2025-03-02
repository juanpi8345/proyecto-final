package com.auth.proyectofinal.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth.proyectofinal.exception.PermissionDeniedException;
import com.auth.proyectofinal.exception.ResourceNotFoundException;
import com.auth.proyectofinal.model.dto.ResponseMessageDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseMessageDTO> handlerResourceNotFound(ResourceNotFoundException e){
		ResponseMessageDTO response = new ResponseMessageDTO(e.getMessage(), HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(PermissionDeniedException.class)
	public ResponseEntity<ResponseMessageDTO> handlePermissionDenied(PermissionDeniedException e){
		ResponseMessageDTO response = new ResponseMessageDTO(e.getMessage(), HttpStatus.FORBIDDEN.value());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ResponseMessageDTO> handlerBadCredentials(BadCredentialsException e){
		ResponseMessageDTO response = new ResponseMessageDTO(e.getMessage(), HttpStatus.FORBIDDEN.value());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}
	
	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ResponseMessageDTO> handlerAuthorizationDenied(AuthorizationDeniedException e){
		ResponseMessageDTO response = new ResponseMessageDTO("You do not have permissions to access this resource", HttpStatus.FORBIDDEN.value());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}
	

}
