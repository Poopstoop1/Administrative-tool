package com.project.Mesa.Validation.validators.filial;

import org.springframework.stereotype.Component;

import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Service.exception.ValidationException;
import com.project.Mesa.Validation.BaseValidation;

@Component
public class FilialRazaoSocialValidator implements BaseValidation<FilialRequestDTO>{

	private final FilialRazaoSocialExistenteValidator razaoSocialExistente;
	
	public FilialRazaoSocialValidator(FilialRazaoSocialExistenteValidator razaoSocialExistente) {
		this.razaoSocialExistente = razaoSocialExistente;
	}
	
	@Override
	public void validar(FilialRequestDTO dto) {

        if (dto.razaosocial() == null || dto.razaosocial().isBlank()) {
            throw new ValidationException("Razao Social  obrigatório: digite um nome para o usuário!");
        }

        if (!dto.razaosocial().matches("^[A-Za-zÀ-ÿ ]{3,100}$")) {
            if (dto.razaosocial().length() < 3 || dto.razaosocial().length() > 100) {
                throw new ValidationException("Razao Social inválido: deve conter entre 3 e 100 caracteres!");
            }
            throw new ValidationException("Razao Social: use apenas letras!");
        }
        
        if(razaoSocialExistente.buscarRazaoSocialExistente(dto.razaosocial())) {
        	throw new ValidationException("Nome inválido: Nome já existe");
        }
	}

}
