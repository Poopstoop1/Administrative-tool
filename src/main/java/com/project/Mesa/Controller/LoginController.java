package com.project.Mesa.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.project.Mesa.Controller.dto.auth.LoginRequestDTO;
import com.project.Mesa.Controller.dto.auth.LoginResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "LoginController", description = "Login Controller")
public interface LoginController {
	
	@Operation(summary = "Realizar login", description = "Autentica o usuário e retorna um token JWT")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "Login realizado com sucesso",
			content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "Dados de login inválidos",
			content = @Content
		),
		@ApiResponse(
			responseCode = "401",
			description = "Credenciais inválidas",
			content = @Content
		)
	})
	@PostMapping
	public ResponseEntity<LoginResponseDTO> login(
			@RequestBody @Validated LoginRequestDTO dto
	);
	
}
