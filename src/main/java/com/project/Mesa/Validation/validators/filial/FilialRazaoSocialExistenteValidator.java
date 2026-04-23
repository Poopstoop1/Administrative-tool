package com.project.Mesa.Validation.validators.filial;

import org.springframework.stereotype.Component;

import com.project.Mesa.Repository.FilialRepository;

@Component
public class FilialRazaoSocialExistenteValidator {
	
	private final FilialRepository filialrepository;
	
	public FilialRazaoSocialExistenteValidator(FilialRepository filialrepository) {
		this.filialrepository = filialrepository;
	}
	
	public Boolean buscarRazaoSocialExistente(String razaoSocial) {
		return filialrepository.existsByRazaoSocial(razaoSocial);
	}

}
