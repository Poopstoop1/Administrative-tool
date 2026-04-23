package com.project.Mesa.Service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Controller.dto.usuario.UsuarioResponseDTO;
import com.project.Mesa.Mapper.impl.UsuarioMapperImpl;
import com.project.Mesa.Model.Filial;
import com.project.Mesa.Model.Usuario;
import com.project.Mesa.Repository.FilialRepository;
import com.project.Mesa.Repository.UsuarioRepository;
import com.project.Mesa.Service.exception.ResourceNotFoundException;
import com.project.Mesa.Validation.validators.CNPJValidator;
import com.project.Mesa.Validation.validators.usuario.UsuarioValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
	private final UsuarioValidator usuarioValidator;
	private final UsuarioRepository usuarioRepository;
	private final UsuarioMapperImpl usuarioMapper;
	private final FilialRepository filialRepository;
	private final CNPJValidator cnpjValidator;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public UsuarioResponseDTO adicionarUsuario(UsuarioRequestDTO usuarioDTO) {
		
		this.usuarioValidator.validaCamposdoUsuario(usuarioDTO);
		
		String cnpjLimpo = cnpjValidator.validarCNPJ(usuarioDTO.cnpjFilial());
		Usuario usuarioNovo = this.usuarioMapper.toUsuario(usuarioDTO);
		
		Filial filial = filialRepository.findById(cnpjLimpo).orElseThrow(() -> new ResourceNotFoundException("Filial não encontrada!!!"));
		usuarioNovo.setPassword(passwordEncoder.encode(usuarioDTO.password()));
		usuarioNovo.setEmpresa(filial);
		return this.usuarioMapper.toUsuarioResponseDTO(this.usuarioRepository.save(usuarioNovo));
	}

	@Transactional
	public UsuarioResponseDTO listarUsuario(Long id) {
		Usuario usuarioListado = usuarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado!"));

		return this.usuarioMapper.toUsuarioResponseDTO(usuarioListado);
	}

	@Transactional
	public List<UsuarioResponseDTO> listarUsuarios() {
		return usuarioRepository.findAll().stream().map(usuarioMapper::toUsuarioResponseDTO).toList();
	}

	@Transactional
	public void deletarUsuario(Long id) {
		Usuario usuarioBuscado = usuarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado!"));

		usuarioRepository.delete(usuarioBuscado);
	}

	@Transactional
	public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO usuarioEditado) {
		Usuario usuarioBuscado = usuarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado!"));

		this.usuarioValidator.validaDadosEditados(usuarioEditado, id);

		this.usuarioMapper.atualizaCampos(usuarioBuscado, usuarioEditado);

		return this.usuarioMapper.toUsuarioResponseDTO(this.usuarioRepository.save(usuarioBuscado));
	}

}
