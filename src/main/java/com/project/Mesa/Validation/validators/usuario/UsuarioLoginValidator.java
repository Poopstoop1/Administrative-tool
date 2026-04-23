package com.project.Mesa.Validation.validators.usuario;

import org.springframework.stereotype.Component;

import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Service.exception.ValidationException;
import com.project.Mesa.Validation.BaseValidation;

@Component
public class UsuarioLoginValidator implements BaseValidation<UsuarioRequestDTO>{

	private final UsuarioLoginExistenteValidator existValidator;
	
	public UsuarioLoginValidator(UsuarioLoginExistenteValidator existValidator) {
		this.existValidator = existValidator;
	}
	
	@Override
	public void validar(UsuarioRequestDTO dto) {
		 if (dto.login() == null || dto.login().isBlank()) {
	            throw new ValidationException("Login obrigatório");
	        }
		 
		 	if (!dto.login().matches("^[a-zA-Z0-9]{4,20}$")) {
			    throw new ValidationException("Login deve ter entre 4 e 20 caracteres e não conter símbolos");
			}
	        
	        if (existValidator.buscarLoginExistente(dto.login())) {
	            throw new ValidationException("Login já existe");
	        }

	}


}
