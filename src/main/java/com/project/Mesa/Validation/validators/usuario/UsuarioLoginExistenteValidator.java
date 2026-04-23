package com.project.Mesa.Validation.validators.usuario;

import org.springframework.stereotype.Component;

import com.project.Mesa.Repository.UsuarioRepository;

@Component
public class UsuarioLoginExistenteValidator  {
	
	private final UsuarioRepository usuarioRepository;
	
	public UsuarioLoginExistenteValidator(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
		
	public Boolean buscarLoginExistente(String login) {
		return usuarioRepository.existsByLogin(login);
	}

}
