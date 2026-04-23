package com.project.Mesa.Validation.validators.campanha;

import org.springframework.stereotype.Component;

import com.project.Mesa.Controller.dto.campanha.CampanhaRequestDTO;
import com.project.Mesa.Validation.BaseValidation;
import com.project.Mesa.Validation.validators.CNPJExistenteValidator;
import com.project.Mesa.Validation.validators.CNPJValidator;

@Component
public class CampanhaCNPJFIlialValidator implements BaseValidation<CampanhaRequestDTO> {

	private final CNPJValidator cnpjValidator;
	private final CNPJExistenteValidator cnpjExistente;

	public CampanhaCNPJFIlialValidator(CNPJValidator cnpjValidator, CNPJExistenteValidator cnpjExistente) {
		this.cnpjValidator = cnpjValidator;
		this.cnpjExistente = cnpjExistente;
	}

	@Override
	public void validar(CampanhaRequestDTO dto) {
		String cnpjLimpo = cnpjValidator.validarCNPJ(dto.cnpj());
		cnpjExistente.validarExistente(cnpjLimpo);
	}

}
