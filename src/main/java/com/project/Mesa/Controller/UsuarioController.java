package com.project.Mesa.Controller;

import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Controller.dto.usuario.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Usuario Controller", description = "Crud de Usuario")
public interface UsuarioController {

	@Operation(summary = "Criar Usuario", description = "Cria um úsuario no banco")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Usuario criado com sucesso", 
					content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Erro ao tentar criar usuario", content = @Content) })
	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO);

	@Operation(summary = "Listar Usuario", description = "Buscar todos os úsuarios")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Lista de Usuario retornada", 
				content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class)))),
	})
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios();
	
	
	@Operation(summary = "RemoverUsuario", description = "Deletar Usuario por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Lista de Usuario retornada", content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
		@ApiResponse(responseCode = "404", description = "Usuario id não encontrado")
	})
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> removerUsuario(@PathVariable Long id);

	@Operation(summary = "EditarUsuario", description = "Atualiza Usuario no banco por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Lista de Usuario retornada", content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
		@ApiResponse(responseCode = "404", description = "Usuario id não encontrado")
	})
	@PutMapping(path = "/{id}")
	public ResponseEntity<UsuarioResponseDTO> editarUsuario(@PathVariable Long id,
			@RequestBody UsuarioRequestDTO usuarioRequestDTO);
}
