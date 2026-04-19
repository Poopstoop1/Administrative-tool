package com.project.Mesa.Mapper;

import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Controller.dto.usuario.UsuarioResponseDTO;
import com.project.Mesa.Model.Usuario;

public interface UsuarioMapper {
		Usuario toUsuario(UsuarioRequestDTO usuarioRequestDTO);
		UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario);
		void atualizaCampos(Usuario usuario, UsuarioRequestDTO dto);	
}
