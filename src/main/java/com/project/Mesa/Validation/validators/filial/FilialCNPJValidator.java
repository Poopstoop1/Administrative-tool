package com.project.Mesa.Validation.validators.filial;

import org.springframework.stereotype.Component;

import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Validation.BaseValidation;
import com.project.Mesa.Validation.validators.CNPJExistenteValidator;
import com.project.Mesa.Validation.validators.CNPJValidator;

@Component
public class FilialCNPJValidator implements BaseValidation<FilialRequestDTO> {

	private final CNPJValidator cnpjValidator;
	private final CNPJExistenteValidator cnpjExistente;

	public FilialCNPJValidator(CNPJValidator cnpjValidator, CNPJExistenteValidator cnpjExistente) {
		this.cnpjValidator = cnpjValidator;
		this.cnpjExistente = cnpjExistente;
	}

	@Override
	public void validar(FilialRequestDTO dto) {
		String cnpjLimpo = cnpjValidator.validarCNPJ(dto.cnpj());
		cnpjExistente.validarNaoExistente(cnpjLimpo);
	}

}
