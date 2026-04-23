package com.project.Mesa.Validation.validators.filial;

import org.springframework.stereotype.Component;

import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Service.exception.ValidationException;
import com.project.Mesa.Validation.BaseValidation;
import com.project.Mesa.Validation.validators.NomeValidator;

@Component
public class FilialNomeValidator implements BaseValidation<FilialRequestDTO>{

	private final FilialNomeExistenteValidator nomeExistente;
	private final NomeValidator nomeValidator;
	
	public FilialNomeValidator(FilialNomeExistenteValidator nomeExistente, NomeValidator nomeValidator) {
		this.nomeExistente = nomeExistente;
		this.nomeValidator = nomeValidator;
	}
	
	@Override
	public void validar(FilialRequestDTO dto) {

       String nomeValidado = nomeValidator.validarNome(dto.nome(), 3, 100);
       
        if(nomeExistente.BuscarNomeExistente(nomeValidado)) {
        	throw new ValidationException("Nome inválido: Nome já existe");
        }
	}

}
