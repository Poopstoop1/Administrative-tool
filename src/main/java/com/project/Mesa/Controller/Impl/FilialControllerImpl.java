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

import com.project.Mesa.Controller.FilialController;
import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Controller.dto.filial.FilialResponseDTO;
import com.project.Mesa.Service.FilialService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/filial")
public class FilialControllerImpl implements FilialController {

	private final FilialService filialService;

	@PostMapping
	public ResponseEntity<FilialResponseDTO> criarFilial(@RequestBody FilialRequestDTO filialRequestDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(filialService.adicionarFilial(filialRequestDTO));
	}
	
	@GetMapping
	public ResponseEntity<List<FilialResponseDTO>> BuscarFiliais() {
		return ResponseEntity.status(HttpStatus.OK).body(filialService.listarFiliais());
	}
	
	@PutMapping(path = "/{cnpj}")
	public ResponseEntity<FilialResponseDTO> Editarfilial(@PathVariable String cnpj, @RequestBody FilialRequestDTO filialRequestDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(filialService.editarFilial(cnpj, filialRequestDTO));
	}
	
	@DeleteMapping(path = "/{cnpj}")
	public ResponseEntity<Void> RemoverFilial(@PathVariable String cnpj) {
		this.filialService.removerFilial(cnpj);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	

}
