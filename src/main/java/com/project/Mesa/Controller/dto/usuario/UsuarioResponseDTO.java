package com.project.Mesa.Controller.dto.usuario;

import com.project.Mesa.Model.Usuario;

public record UsuarioResponseDTO(Long id, String nome, String login, String cargo) {

	public static UsuarioResponseDTO fromUsuarioResponseDTO(Usuario usuario) {
		return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getCargo());
	}

}
