package com.project.Mesa.Validation.validators.filial;

import org.springframework.stereotype.Component;

import com.project.Mesa.Repository.FilialRepository;

@Component
public class FilialNomeExistenteValidator  {
	
	private final FilialRepository filialrepository;
	
	public FilialNomeExistenteValidator(FilialRepository filialrepository) {
		this.filialrepository = filialrepository;
	}
	
	public Boolean BuscarNomeExistente(String nome) {
		return filialrepository.existsByNome(nome);
	}
}
