package com.project.Mesa.Validation.validators.filial;

import java.util.List;

import org.springframework.stereotype.Component;

import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Model.Filial;
import com.project.Mesa.Repository.FilialRepository;
import com.project.Mesa.Service.exception.ResourceNotFoundException;
import com.project.Mesa.Service.exception.ValidationException;
import com.project.Mesa.Validation.BaseValidation;

@Component
public class FilialValidator {

	private final List<BaseValidation<FilialRequestDTO>> validators;

	private final FilialRepository filialRepository;

	public FilialValidator(List<BaseValidation<FilialRequestDTO>> validators, FilialRepository filialRepository) {
		this.validators = validators;
		this.filialRepository = filialRepository;
	}

	public void validarCamposDeFilial(FilialRequestDTO filialRequestDTO) {
		if (filialRequestDTO == null)
			throw new ValidationException("Digite as informações de Filial");

		validators.forEach(v -> v.validar(filialRequestDTO));
	}

	public void validarDadosEditado(FilialRequestDTO filialEditado, String cnpjFilialAtual) {
		if (filialEditado == null)
			throw new ValidationException("Dados de filial não informados");

		Filial filialAtual = filialRepository.findById(cnpjFilialAtual).orElseThrow(
				() -> new ResourceNotFoundException("Filial com CNPJ" + cnpjFilialAtual + "Não Encontrado"));

		if (filialEditado.cnpj() != null && !filialEditado.cnpj().isBlank()) {
			String cnpjLimpo = filialEditado.cnpj().replaceAll("\\D", "");

			if (cnpjLimpo.length() != 14) {
				throw new ValidationException("Erro ao editar: O CPNJ deve ter 14 dígitos.");
			}
			if (cnpjLimpo.matches("(\\d)\\1{13}"))
				throw new ValidationException("CNPJ inválido: numeros repetidos!");
			
			if (cnpjLimpo.equals(cnpjFilialAtual)) {
				throw new ValidationException("Erro ao editar: CNPJ é o mesmo, informe outro.");
			}

		} else {
			throw new ValidationException("Erro ao Editar: CNPJ não informado.");
		}
		
		if (filialEditado.nome() != null && !filialEditado.nome().isBlank()) {
			if (filialEditado.nome().equals(filialAtual.getNome()))
				throw new ValidationException("Erro ao editar: O Nome é o mesmo, informe outro.");
			if (!filialEditado.nome().matches("^[A-Za-zÀ-ÿ ]{3,100}$")) {
				if (filialEditado.nome().length() < 3 || filialEditado.nome().length() > 100) {
					throw new ValidationException("Nome inválido: deve conter entre 3 e 100 caracteres!");
				}
				throw new ValidationException("Nome inválido: use apenas letras!");
			}
		} else {
			throw new ValidationException("Erro ao Editar: digite um nome para o usuário!");
		}
		
		if (filialEditado.razaosocial() != null && !filialEditado.razaosocial().isBlank()) {
			if (filialEditado.razaosocial().equals(filialAtual.getRazaoSocial()))
				throw new ValidationException("Erro ao editar: O Nome é o mesmo, informe outro.");
			if (!filialEditado.razaosocial().matches("^[A-Za-zÀ-ÿ ]{3,100}$")) {
				if (filialEditado.razaosocial().length() < 3 || filialEditado.razaosocial().length() > 100) {
					throw new ValidationException("Nome inválido: deve conter entre 3 e 100 caracteres!");
				}
				throw new ValidationException("Nome inválido: use apenas letras!");
			}
		} else {
			throw new ValidationException("Erro ao Editar: digite um nome para o usuário!");
		}

	}

}
