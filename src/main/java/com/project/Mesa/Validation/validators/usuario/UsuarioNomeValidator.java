package com.project.Mesa.Validation.validators.usuario;

import org.springframework.stereotype.Component;

import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Validation.BaseValidation;
import com.project.Mesa.Validation.validators.NomeValidator;

@Component
public class UsuarioNomeValidator implements BaseValidation<UsuarioRequestDTO> {

	private final NomeValidator nomeValidator;
	
	public UsuarioNomeValidator(NomeValidator nomeValidator) {
		this.nomeValidator = nomeValidator;
	}
	
	@Override
	public void validar(UsuarioRequestDTO dto) {
		nomeValidator.validarNome(dto.nome(), 3, 80);
	}
}
