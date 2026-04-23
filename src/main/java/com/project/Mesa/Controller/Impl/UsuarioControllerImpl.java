package com.project.Mesa.Controller.Impl;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Mesa.Controller.UsuarioController;
import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Controller.dto.usuario.UsuarioResponseDTO;
import com.project.Mesa.Service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/usuario")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioControllerImpl implements UsuarioController {

	private final UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioService.adicionarUsuario(usuarioRequestDTO));
	}
	
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> removerUsuario(@PathVariable Long id){
		usuarioService.deletarUsuario(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<UsuarioResponseDTO> editarUsuario(@PathVariable Long id,@RequestBody UsuarioRequestDTO usuarioRequestDTO){
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.atualizarUsuario(id, usuarioRequestDTO));
	}
}
