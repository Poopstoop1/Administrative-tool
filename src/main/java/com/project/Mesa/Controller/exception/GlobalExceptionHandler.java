package com.project.Mesa.Controller.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.project.Mesa.Service.exception.ResourceNotFoundException;
import com.project.Mesa.Service.exception.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> resourceNotFound(ResourceNotFoundException ex, WebRequest request) {
		ApiError error = new ApiError("Not found Exception", ex.getMessage(), HttpStatus.NOT_FOUND.value(),
				request.getDescription(false).replace("uri=", ""), LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ApiError> usuarioValidation(ValidationException ex, WebRequest request) {
		ApiError error = new ApiError("Validation Field Exception", ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
				request.getDescription(false).replace("uri=", ""), LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiError> dataIntegratyViolation(DataIntegrityViolationException ex, WebRequest request) {
		ApiError error = new ApiError("Validation Field Exception", "Operação não permitida: já há dados iguais cadastrado.", HttpStatus.BAD_REQUEST.value(),
				request.getDescription(false).replace("uri=", ""), LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
