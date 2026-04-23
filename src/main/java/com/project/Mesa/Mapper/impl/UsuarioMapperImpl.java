package com.project.Mesa.Mapper.impl;

import org.springframework.stereotype.Component;
import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Controller.dto.usuario.UsuarioResponseDTO;
import com.project.Mesa.Mapper.UsuarioMapper;
import com.project.Mesa.Model.Usuario;

@Component
public class UsuarioMapperImpl implements UsuarioMapper {

	@Override
	public Usuario toUsuario(UsuarioRequestDTO usuarioRequestDTO) {
		Usuario usuario = new Usuario();
		usuario.setNome(usuarioRequestDTO.nome());
		usuario.setLogin(usuarioRequestDTO.login());
		usuario.setCargo(usuarioRequestDTO.cargo());
		return usuario;
	}

	@Override
	public UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario) {
		return UsuarioResponseDTO.fromUsuarioResponseDTO(usuario);
	}

	@Override
	public void atualizaCampos(Usuario usuario, UsuarioRequestDTO dtoEditado) {
		if (dtoEditado.nome() != null && !dtoEditado.nome().isBlank())
			usuario.setNome(dtoEditado.nome());

		if (dtoEditado.login() != null && !dtoEditado.login().isBlank())
			usuario.setLogin(dtoEditado.login());

		if (dtoEditado.password() != null && !dtoEditado.password().isBlank())
			usuario.setPassword(dtoEditado.password());
	}

}
