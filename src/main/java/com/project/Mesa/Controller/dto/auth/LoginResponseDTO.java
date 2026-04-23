package com.project.Mesa.Controller.dto.auth;

public record LoginResponseDTO(String token) {
	 public LoginResponseDTO fromLoginResponseDTO(String token) {
		return new LoginResponseDTO(token);
	} 
}
