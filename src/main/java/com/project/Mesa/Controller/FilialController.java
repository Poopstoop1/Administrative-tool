package com.project.Mesa.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Controller.dto.filial.FilialResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Filial Controller", description = "Crud de Filial Controller")
public interface FilialController {

	@Operation(summary = "Criar Filial", description = "Criação de Filial no Banco de dados")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Filial criada com sucesso", content = @Content(schema = @Schema(implementation = FilialResponseDTO.class))),
		@ApiResponse(responseCode = "400", description = "Error ao tentar criar filial", content = @Content)
	})
	@PostMapping
	public ResponseEntity<FilialResponseDTO> criarFilial(@RequestBody FilialRequestDTO filialRequestDTO);
	
	@Operation(summary = "BuscarFiliais", description = "Listar Filiais")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Lista de Filiais retornada", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FilialResponseDTO.class))))
	})
	@GetMapping
	public ResponseEntity<List<FilialResponseDTO>> BuscarFiliais();
	
	@Operation(summary = "Editar Filial", description = "Editar Filial no Banco de dados")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Filial editada com sucesso", content = @Content(schema = @Schema(implementation = FilialResponseDTO.class))),
		@ApiResponse(responseCode = "404", description = "Error Filial não encontrado", content = @Content)
	})
	@PutMapping(path = "/{cnpj}")
	public ResponseEntity<FilialResponseDTO> Editarfilial(@Parameter(description = "CNPJ de Filial", required = true, schema = @Schema(type = "string"))@PathVariable String cnpj, @RequestBody FilialRequestDTO filialRequestDTO);
	
	@Operation(summary = "RemoverFilial", description = "Remove Filial do Banco de dados")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Filial removida com sucesso", content = @Content(schema = @Schema(implementation = FilialResponseDTO.class))),
		@ApiResponse(responseCode = "404", description = "Error Filial não encontrada", content = @Content)
	})
	@DeleteMapping(path = "/{cnpj}")
	public ResponseEntity<Void> RemoverFilial(@Parameter(description = "CNPJ de Filial", required = true, schema = @Schema(type = "string"))@PathVariable String cnpj);
}
