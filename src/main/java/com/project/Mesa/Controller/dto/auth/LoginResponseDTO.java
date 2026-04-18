package com.project.Mesa.Controller.dto.auth;

import com.project.Mesa.Model.Usuario;

public record LoginResponseDTO(String token, String username, String role) {
	 public LoginResponseDTO fromLoginResponseDTO(String token, Usuario usuario) {
		return new LoginResponseDTO(token, usuario.getUsername(), usuario.getCargo());
	} 
}
