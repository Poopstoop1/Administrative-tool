package com.project.Mesa.Validation.validators.usuario;

import org.springframework.stereotype.Component;

import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Service.exception.ValidationException;
import com.project.Mesa.Validation.BaseValidation;

@Component
public class UsuarioCargoValidator implements BaseValidation<UsuarioRequestDTO>{

	@Override
	public void validar(UsuarioRequestDTO dto) {
		if(dto.cargo() == null || dto.cargo().isBlank())
			throw new ValidationException("Cargo é obrigatorio.");
		
		if (!dto.cargo().matches("^[a-zA-Z0-9À-ÿ]{3,30}$")) {
			if (dto.cargo().length() < 3 || dto.cargo().length() > 30) {
                throw new ValidationException("Cargo inválido: deve conter entre 3 e 80 caracteres!");
            }
		    throw new ValidationException("Cargo inválido: não pode conter espaços ou símbolos.");
		}
		
	}
	


}
