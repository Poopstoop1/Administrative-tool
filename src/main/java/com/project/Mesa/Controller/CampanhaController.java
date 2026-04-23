package com.project.Mesa.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.project.Mesa.Controller.dto.campanha.CampanhaResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Campanha Controller", description = "Funções para Campanha")
public interface CampanhaController {

	@Operation(summary = "FindCampanhaByPagina", description = "Busca a lista de Campanha por Pagina")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Campanha encontrada con sucesso"),
		@ApiResponse(responseCode = "404", description = "Campanha não encontrada")
	})
	@GetMapping(path = "/{pagina}")
	public ResponseEntity<List<CampanhaResponseDTO>> getFindCampanhaByPagina(@Parameter(description = "Pagina de Campanha", required = true, schema = @Schema(type = "string"))@PathVariable String pagina);

	@Operation(summary = "importaCampanha", description = "Importa dados de campanhas da Planilha Online")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Campanhas importadas com sucesso.",
					content = @Content(schema = @Schema(implementation = CampanhaResponseDTO.class))),
			@ApiResponse(responseCode = "500", description = "Erro ao importar dados do Google Sheets", content = @Content) })
	@PostMapping(path = "/importarCampanha")
	public ResponseEntity<List<CampanhaResponseDTO>> importarCampanhasGoogleSheet();
	

}
