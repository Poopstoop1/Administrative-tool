package com.project.Mesa.Validation.validators;

import org.springframework.stereotype.Component;

import com.project.Mesa.Repository.FilialRepository;
import com.project.Mesa.Service.exception.ValidationException;

@Component
public class CNPJExistenteValidator {

	private final FilialRepository filialRepository;

	public CNPJExistenteValidator(FilialRepository filialRepository) {
		this.filialRepository = filialRepository;
	}

	public void validarExistente(String cnpj) {
		if (!filialRepository.existsById(cnpj)) {
			throw new ValidationException("Filial não encontrada: CNPJ não cadastrado");
		}
	}

	public void validarNaoExistente(String cnpj) {
		if (filialRepository.existsById(cnpj)) {
			throw new ValidationException("CNPJ já cadastrado no sistema");
		}
	}
}
