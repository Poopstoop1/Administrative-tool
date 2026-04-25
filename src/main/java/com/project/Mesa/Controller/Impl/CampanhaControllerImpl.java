package com.project.Mesa.Controller.Impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Mesa.Controller.CampanhaController;
import com.project.Mesa.Controller.dto.campanha.CampanhaResponseDTO;
import com.project.Mesa.Service.CampanhaService;
import com.project.Mesa.Service.GoogleSheetsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/campanha")
@CrossOrigin(origins = "*")
public class CampanhaControllerImpl implements CampanhaController {

	private final CampanhaService campanhaService;
	private final GoogleSheetsService googleSheetService;
	
	@GetMapping(path = "/{pagina}")
	public ResponseEntity<List<CampanhaResponseDTO>> getFindCampanhaByPagina(@PathVariable String pagina) {
		return ResponseEntity.status(HttpStatus.OK).body(this.campanhaService.ListarCampanhaPorPagina(pagina));
	}
	
	@PostMapping(path = "/importarCampanha")
	public ResponseEntity<List<CampanhaResponseDTO>> importarCampanhasGoogleSheet(){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(googleSheetService.loadDataFromGoogleSheets());
		} catch (IOException | GeneralSecurityException e) {
			return ResponseEntity.internalServerError().build();
		}	
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> removerCampanha(Long id) {
		campanhaService.removerCampanha(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	
	
}