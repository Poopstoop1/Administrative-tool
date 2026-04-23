package com.project.Mesa.Validation.validators.usuario;

import org.springframework.stereotype.Component;

import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Service.exception.ValidationException;
import com.project.Mesa.Validation.BaseValidation;

@Component
public class UsuarioPasswordValidator implements BaseValidation<UsuarioRequestDTO> {

	@Override
	public void validar(UsuarioRequestDTO dto) {
		if (dto.password() == null || dto.password().isBlank()) {
			throw new ValidationException("Password Obrigatorio: Digite o Password.");
		}
		
		if (!dto.password().matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,32}$")) {
			if (dto.password().length() < 8 || dto.password().length() > 32) {
                throw new ValidationException("Senha inválida: deve conter entre 8 e 32 caracteres!");
            }
		    throw new ValidationException("Senha inválida: Deve ter uma letra maiúscula, um número e um caractere especial");
		}

	}

}
