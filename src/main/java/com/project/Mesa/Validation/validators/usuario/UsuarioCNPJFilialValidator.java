package com.project.Mesa.Validation.validators.usuario;

import org.springframework.stereotype.Component;
import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Validation.BaseValidation;
import com.project.Mesa.Validation.validators.CNPJExistenteValidator;
import com.project.Mesa.Validation.validators.CNPJValidator;

@Component
public class UsuarioCNPJFilialValidator implements BaseValidation<UsuarioRequestDTO> {

	private final CNPJValidator cnpjValidator;
	private final CNPJExistenteValidator cnpjExistente;

	public UsuarioCNPJFilialValidator(CNPJValidator cnpjValidator, CNPJExistenteValidator cnpjExistente) {
		this.cnpjValidator = cnpjValidator;
		this.cnpjExistente = cnpjExistente;
	}

	@Override
	public void validar(UsuarioRequestDTO dto) {
		String cnpjLimpo = cnpjValidator.validarCNPJ(dto.cnpjFilial());
		cnpjExistente.validarExistente(cnpjLimpo);
	}

}
