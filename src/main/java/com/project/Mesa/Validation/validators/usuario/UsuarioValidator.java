package com.project.Mesa.Validation.validators.usuario;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Model.Usuario;
import com.project.Mesa.Repository.UsuarioRepository;
import com.project.Mesa.Service.exception.ResourceNotFoundException;
import com.project.Mesa.Service.exception.ValidationException;
import com.project.Mesa.Validation.BaseValidation;

@Component
public class UsuarioValidator {
	private final List<BaseValidation<UsuarioRequestDTO>> validators;

	private final UsuarioRepository usuarioRepository;

	private final PasswordEncoder passwordEncoder;

	public UsuarioValidator(List<BaseValidation<UsuarioRequestDTO>> validators, UsuarioRepository usuarioRepository,
			PasswordEncoder passwordEncoder) {
		this.validators = validators;
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void validaCamposdoUsuario(UsuarioRequestDTO dto) {
		if (dto == null)
			throw new ValidationException("Digite as Informações do usuário.");

		validators.forEach(v -> v.validar(dto));
	}

	public void validaDadosEditados(UsuarioRequestDTO usuarioEditado, Long idAtual) {
		if (usuarioEditado == null)
			throw new ValidationException("Dados do usuario não informado.");

		Usuario usuarioAtual = usuarioRepository.findById(idAtual)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário de id "+ idAtual + " Não encontrado"));
		
		
		if (usuarioEditado.nome() != null && !usuarioEditado.nome().isBlank()) {
			if (usuarioEditado.nome().equals(usuarioAtual.getNome()))
				throw new ValidationException("Erro ao editar: O Nome é o mesmo, informe outro.");
			
			if (!usuarioEditado.nome().matches("^[A-Za-zÀ-ÿ ]{3,80}$")) {
				if (usuarioEditado.nome().length() < 3 || usuarioEditado.nome().length() > 80) {
					throw new ValidationException("Nome inválido: deve conter entre 3 e 80 caracteres!");
				}
				throw new ValidationException("Nome inválido: use apenas letras!");
			}
		} else {
			throw new ValidationException("Erro ao Editar: digite um nome para o usuário!");
		}

		if (usuarioEditado.login() != null && !usuarioEditado.login().isBlank()) {
			usuarioRepository.findByUsername(usuarioEditado.login()).ifPresent(u -> {
				if (!u.getId().equals(idAtual))
					throw new ValidationException("Erro ao Editar: Login já está em uso.");
			});
			if (!usuarioEditado.login().matches("^[a-zA-Z0-9]{4,20}$")) {
				throw new ValidationException(
						"Erro Ao Editar: Login deve ter entre 4 e 20 caracteres e não conter símbolos");
			}
		} else {
			throw new ValidationException("Erro ao Editar: Login não informado.");
		}

		if (usuarioEditado.password() != null && !usuarioEditado.password().isBlank()) {

			if(passwordEncoder.matches(usuarioEditado.password(), usuarioAtual.getPassword())) {
				throw new ValidationException("Erro ao Editar: Senha igual a atual.");
			}
			
			if (usuarioEditado.password().equals(usuarioAtual.getPassword()))
				throw new ValidationException("Erro ao editar: O Password é o mesmo, informe outro.");

			if (!usuarioEditado.password().matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,32}$")) {
				if (usuarioEditado.password().length() < 8 || usuarioEditado.password().length() > 32) {
					throw new ValidationException("Erro ao Editar:Senha deve conter entre 8 e 32 caracteres!");
				}
				throw new ValidationException(
						"Erro ao Editar: Senha deve ter uma letra maiúscula, um número e um caractere especial");
			}

		} else {
			throw new ValidationException("Erro ao Editar: Senha não informada.");
		}

		if (usuarioEditado.cargo() != null && !usuarioEditado.cargo().isBlank()) {
			if (usuarioEditado.cargo().equals(usuarioAtual.getCargo()))
				throw new ValidationException("Erro ao editar: O Cargo é o mesmo, informe outro.");

			if (!usuarioEditado.cargo().matches("^[a-zA-Z0-9À-ÿ]{3,30}$"))
				throw new ValidationException(
						"Erro ao Editar: Cargo deve ter entre 3 a 30 caracteres e não pode conter espaços ou símbolos.");

		} else {
			throw new ValidationException("Erro ao Editar: Cargo não informado.");
		}

		if (usuarioEditado.cnpjFilial() != null && !usuarioEditado.cnpjFilial().isBlank()) {
			String cnpjLimpo = usuarioEditado.cnpjFilial().replaceAll("\\D", "");

			if (cnpjLimpo.length() != 14) {
				throw new ValidationException("Erro ao editar: O CPNJ deve ter 14 dígitos.");
			}
			if (cnpjLimpo.matches("(\\d)\\1{13}"))
				throw new ValidationException("CNPJ inválido: numeros repetidos!");

			if (cnpjLimpo.equals(usuarioAtual.getEmpresa().getCnpj())) {
				throw new ValidationException("Erro ao editar: CNPJ é o mesmo, informe outro.");
			}

		} else {
			throw new ValidationException("Erro ao Editar: CNPJ não informado.");
		}

	}

}
