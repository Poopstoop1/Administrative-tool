package com.project.Mesa.Validation.validators;

import org.springframework.stereotype.Component;

import com.project.Mesa.Service.exception.ValidationException;

@Component
public class CNPJValidator {

	public String validarCNPJ(String cnpj) {
		if (cnpj == null || cnpj.isBlank()) {
			throw new ValidationException("CNPJ é obrigatório");
		}

		String cnpjLimpo = cnpj.replaceAll("\\D", "");

		if (cnpjLimpo.length() != 14) {
			throw new ValidationException("CNPJ inválido: Deve conter 14 dígitos");
		}

		if (cnpjLimpo.matches("(\\d)\\1{13}")) {
			throw new ValidationException("CNPJ inválido: números repetidos");
		}

		return cnpjLimpo;
	}

	
}
