package com.project.Mesa.Service;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.project.Mesa.Controller.dto.campanha.CampanhaResponseDTO;
import com.project.Mesa.Mapper.impl.CampanhaMapperImpl;
import com.project.Mesa.Model.Usuario;
import com.project.Mesa.Repository.CampanhaRepository;
import com.project.Mesa.Repository.UsuarioRepository;
import com.project.Mesa.Service.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampanhaService {
	private final CampanhaRepository campanhaRepository;
	private final CampanhaMapperImpl campanhaMapper;
	private final UsuarioRepository usuarioRepository;

	@Transactional
	public List<CampanhaResponseDTO> ListarCampanhaPorPagina(String pagina) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Usuario usuario = this.usuarioRepository.findByUsername(auth.getName())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado!"));

		Boolean isManager = auth.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MANAGER"));

		if (isManager) {
			return campanhaRepository.findByPagina(pagina).stream().map(campanhaMapper::campanhaResponseDTO).toList();
			
		} else if (usuario.getLogin().equals("CarlaFerreira")) {
			return campanhaRepository
					.findByPaginaAndEmpresaCnpjIn(pagina, List.of(usuario.getEmpresa().getCnpj(), "98.765.432/0001-12"))
					.stream().map(campanhaMapper::campanhaResponseDTO).toList();
		}

		return campanhaRepository.findByPaginaAndEmpresaCnpj(pagina, usuario.getEmpresa().getCnpj()).stream()
				.map(campanhaMapper::campanhaResponseDTO).toList();

	}

}
